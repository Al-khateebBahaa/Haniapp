package com.ahmedMustafa.Hani.activity.orders;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.MainActivity;
import com.ahmedMustafa.Hani.adapter.ChatAdapter;
import com.ahmedMustafa.Hani.model.ChatModel;
import com.ahmedMustafa.Hani.model.OrderModel;
import com.ahmedMustafa.Hani.model.PublicModel;
import com.ahmedMustafa.Hani.model.TroodOrderDetailsModel;
import com.ahmedMustafa.Hani.utils.Constant;
import com.ahmedMustafa.Hani.utils.CustomApi;
import com.ahmedMustafa.Hani.utils.GPSTracker;
import com.ahmedMustafa.Hani.utils.base.BaseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChatActivity extends BaseActivity {

    private EditText editMsg;
    private View sendBut, optionBut;
    private RecyclerView recyclerView;
    private DatabaseReference dbRef;
    private String orderId;
    private ChatAdapter adapter;
    private ChildEventListener eventListener;
    private final int REQUEST_IMAGE = 1;
    private PopupMenu popupMenu;
    private Toolbar toolbar;
    private String type;
    private final String STORE = "shops";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        orderId = getIntent().getStringExtra("id");
        type = getIntent().getIntExtra("type", 0) == 0 ? "shops" : "trd";
        bind();
     //   init();
        popup(optionBut);
        onClick();
    }

    private void bind() {
        editMsg = findViewById(R.id.editMsg);
        sendBut = findViewById(R.id.sendBut);
        optionBut = findViewById(R.id.optionIcon);
        recyclerView = findViewById(R.id.recycler);
        toolbar = findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
       // getSupportActionBar().setTitle("");
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    //    setDataFrame(findViewById(R.id.dataLayout), findViewById(R.id.layout));
    }

    private void onClick() {

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        optionBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     popupMenu.show();
                showChatBottomSheet();

            }
        });
        sendBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isConnected()) {
                    toast(R.string.noNetwork);
                } else {
                    if (isValidText(editMsg)) {
                        long time = System.currentTimeMillis();
                        ChatModel model = new ChatModel();
                        model.setMessage_body(editMsg.getText().toString());
                        model.setMessage_type("1");
                        model.setSender_user_id(getPrefManager().getUserId());
                        dbRef.child(time + "").setValue(model);
                        editMsg.setText("");
                    }
                }
            }
        });
    }

    private void init() {
        adapter = new ChatAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setStackFromEnd(true);
        recyclerView.setAdapter(adapter);
        dbRef = FirebaseDatabase.getInstance().getReference().child("chats").child(type).child(orderId).child("messages");

        eventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatModel item = dataSnapshot.getValue(ChatModel.class);
                adapter.addItem(item);
                recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        dbRef.addChildEventListener(eventListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbRef.removeEventListener(eventListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {

            if (isConnected()) {
                showProgress();
                uploadImage(data.getData());
            } else {
                toast(R.string.noNetwork);
            }
        } else if (requestCode == CHANGE_BILL && resultCode == RESULT_OK) {
            long l = System.currentTimeMillis();
            order.setDeliveryFee(Integer.parseInt(data.getStringExtra("price")));
            ChatModel model = new ChatModel();
            model.setMessage_type("1");
            model.setMessage_body("تم تغيير الفاتوره إلي : " + data.getStringExtra("price") + " ريال ");
            model.setSender_user_id(getPrefManager().getUserId());
            dbRef.child(l + "").setValue(model);
        }
    }

    private void uploadImage(Uri uri) {
        final long id = System.currentTimeMillis();
        FirebaseStorage.getInstance().getReference().child(System.currentTimeMillis() + "")
                .putFile(uri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            ChatModel model = new ChatModel();
                            model.setSender_user_id(getPrefManager().getUserId());
                            model.setMessage_body(task.getResult().getDownloadUrl().toString());
                            model.setMessage_type("2");
                            dbRef.child(id + "").setValue(model);
                            hideProgress();
                        } else {
                            hideProgress();
                            toast(task.getException().getMessage());
                        }
                    }
                });
    }

    private void popup(View view) {
        popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.chat, popupMenu.getMenu());
        if (type.equals(STORE)) popupMenu.getMenu().getItem(1).setVisible(false);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.uploadImg:
                        if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 4)) {
                            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, REQUEST_IMAGE);
                        }
                        return true;
                    case R.id.changeBill:
                        String agentI = order.getAgentId() + "";
                        if (agentI.equals(getPrefManager().getUserId())) {
                            Intent i = new Intent(ChatActivity.this, ChangeBillActivity.class);
                            i.putExtra("id", orderId);
                            i.putExtra("price", order.getDeliveryFee());
                            i.putExtra("orderPrice", order.getOrderPrice());
                            startActivityForResult(i, CHANGE_BILL);
                        } else {
                            toast("عفوا المندوب فقط يستطيع تغيير الفاتوره");
                        }
                        return true;
                    case R.id.doneDelivary:
                        String agentId = type.equals(STORE) ? order.getAgentId() + "" : troodOrder.getAgentId() + "";

                        if (!type.equals(STORE)) {
                            troodFishedOrder();
                        } else {
                            if (!agentId.equals(getPrefManager().getUserId())) {
                                toast("عفوا لا يمكن تنفيذ طلبك");
                            } else {
                                if (type.equals(STORE))
                                    finshedOrder();
                            }
                        }

                        return true;
                }
                return false;
            }
        });
    }

    private void finshedOrder() {
        showProgress();
        getApi().doneOrder(orderId)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PublicModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PublicModel model) {
                        hideProgress();
                        if (model.getStatus() == 1) {
                            toast("تم توصيل الطلب بنجاح");
                            ChatModel chatModel = new ChatModel();
                            chatModel.setSender_user_id(getPrefManager().getUserId());
                            chatModel.setMessage_body("تم توصيل الطلب بنجاح");
                            chatModel.setMessage_type("1");
                            long l = System.currentTimeMillis();
                            dbRef.child(l + "").setValue(chatModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    popupRate();
                                }
                            });
                        } else {
                            toast(model.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgress();
                        toast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void troodFishedOrder() {
        showProgress();
        getApi().changeStatusTrood(orderId, "2")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomApi<PublicModel>(new CustomApi.CallApi<PublicModel>() {
                    @Override
                    public void onResponse(PublicModel response) {
                        hideProgress();
                        if (response.getStatus() == 1) {
                            toast("تم توصيل الطرد بنجاح");
                            popupRate();
                        } else {
                            toast(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(String msgError) {
                        hideProgress();
                        toast(msgError);
                    }
                }, "changeStatusTrood"));
    }

    private OrderModel.Order order;
    private TroodOrderDetailsModel.Order troodOrder;
    private final static int CHANGE_BILL = 4;

    @Override
    protected void loadData() {
        super.loadData();
        if (type.equals(STORE)) {
            loadStoreOrder();
        } else {
            loadTroodOrder();
        }

    }

    private void loadStoreOrder() {
        getApi().getOrder(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OrderModel orderModel) {
                        if (orderModel.getStatus() == 1) {
                            dataFrame.setVisible(LAYOUT);
                            order = orderModel.getOrder();
                            getSupportActionBar().setTitle(order.getRestaurantName());
                        } else {
                            onBackPressed();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        onBackPressed();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void loadTroodOrder() {
        getApi().getTroodOrderDetails(orderId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomApi<TroodOrderDetailsModel>(new CustomApi.CallApi<TroodOrderDetailsModel>() {
                    @Override
                    public void onResponse(TroodOrderDetailsModel response) {
                        if (response.getStatus() == 1) {
                            dataFrame.setVisible(LAYOUT);
                            troodOrder = response.getOrder();
                            getSupportActionBar().setTitle(troodOrder.getCompanyName());
                        } else {
                            onBackPressed();
                        }
                    }

                    @Override
                    public void onError(String msgError) {
                        onBackPressed();
                    }
                }, "TroodOrderDetails"));

    }

    private void popupRate() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_rate_user);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Picasso.get().load(Constant.BASE_IMAGE + (type.equals(STORE) ? order.getImage() : troodOrder.getUserIdImage())).into(((ImageView) dialog.findViewById(R.id.image)));
        final RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
        ((TextView) dialog.findViewById(R.id.name)).setText(type.equals(STORE) ? order.getName() : troodOrder.getCompanyName());
        final EditText editComment = dialog.findViewById(R.id.editComment);
        dialog.findViewById(R.id.cancel_but).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.sendBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                if (isValidText(editComment)) {
                    if (rating == 0.0) {
                        toast("يجب تقييم أولا");
                    } else {
                        if (isConnected()) {
                            dialog.dismiss();
                            sendRate(getString(editComment), rating + "");
                        } else {
                            toast(R.string.noNetwork);
                        }
                    }
                }
            }
        });
        dialog.show();
    }

    private void sendRate(String comment, String rate) {
        showProgress();
        getApi().rateUSer(getPrefManager().getUserId(), type.equals(STORE) ? order.getUserId() + "" : troodOrder.getUserId() + "", rate, comment)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PublicModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(PublicModel model) {
                hideProgress();
                if (model.getStatus() == 1) {
                    toast("تم التقييم بنجاح");
                    Intent i = new Intent(ChatActivity.this, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    openActivity(i);
                } else {
                    toast(model.getMsg());
                    popupRate();
                }

            }

            @Override
            public void onError(Throwable e) {
                hideProgress();
                popupRate();
                toast(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void showChatBottomSheet() {

        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);

        mBottomSheetDialog.setContentView(R.layout.chat_bottom_sheet);
        mBottomSheetDialog.show();

    }
}
