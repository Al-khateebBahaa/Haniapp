package com.ahmedMustafa.Hani.utils;

import com.ahmedMustafa.Hani.model.AboutModel;
import com.ahmedMustafa.Hani.model.AganetAvailebleOrderModel;
import com.ahmedMustafa.Hani.model.AgentAcceptedOrderModel;
import com.ahmedMustafa.Hani.model.AllAgetntForTroodModel;
import com.ahmedMustafa.Hani.model.AllCountriresModel;
import com.ahmedMustafa.Hani.model.AssigmentInfoModel;
import com.ahmedMustafa.Hani.model.ChangeNotificationModel;
import com.ahmedMustafa.Hani.model.DeliveryRequestModel;
import com.ahmedMustafa.Hani.model.MResModel;
import com.ahmedMustafa.Hani.model.MakeOrderModel;
import com.ahmedMustafa.Hani.model.MakeTransferModel;
import com.ahmedMustafa.Hani.model.MyOrderModel;
import com.ahmedMustafa.Hani.model.MyOrderTroodModel;
import com.ahmedMustafa.Hani.model.NearbyRestaurantModel;
import com.ahmedMustafa.Hani.model.NotificationModel;
import com.ahmedMustafa.Hani.model.OrderModel;
import com.ahmedMustafa.Hani.model.PayInfoModel;
import com.ahmedMustafa.Hani.model.PopupModel;
import com.ahmedMustafa.Hani.model.PublicModel;
import com.ahmedMustafa.Hani.model.RateModel;
import com.ahmedMustafa.Hani.model.RatingAppLinkModel;
import com.ahmedMustafa.Hani.model.RestaurantAgentModel;
import com.ahmedMustafa.Hani.model.RestaurantOrdersModel;
import com.ahmedMustafa.Hani.model.SingupAsAgentModel;
import com.ahmedMustafa.Hani.model.TermsModel;
import com.ahmedMustafa.Hani.model.TroodAsCompanyModel;
import com.ahmedMustafa.Hani.model.TroodDetailsModel;
import com.ahmedMustafa.Hani.model.TroodOrderDetailsModel;
import com.ahmedMustafa.Hani.model.TroodsModel;
import com.ahmedMustafa.Hani.model.UserInfoModel;
import com.ahmedMustafa.Hani.model.WatingTroodModel;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {


    /*
    user Area

    login - register - edit - get info - rate
     */

    @GET("get-all-countries")
    Observable<AllCountriresModel> getAllCountries();

    @FormUrlEncoded
    @POST("sign-up")
    Observable<UserInfoModel> singUp(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("login")
    Observable<UserInfoModel> loginWithPhone(@Field("phone") String phone, @Field("password") String password, @Field("token") String token, @Field("device_type") String deviceType);

    @FormUrlEncoded
    @POST("login-with-social")
    Observable<UserInfoModel> loginSocial(@Field("id") String id, @Field("image") String image, @Field("name") String name, @Field("email") String email, @Field("phone") String phone, @Field("lat") String lat, @Field("lng") String lng);

    @FormUrlEncoded
    @POST("edit-account")
    Observable<UserInfoModel> editProfile(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("get-user-info")
    Observable<UserInfoModel> getUserInfo(@Field("user_id") String userID);


    @FormUrlEncoded
    @POST("rate-user")
    Observable<PublicModel> rateUSer(@Field("user_id") String userID, @Field("rated_user_id") String rated_user_id, @Field("rate") String rate, @Field("comment") String comment);

    @FormUrlEncoded
    @POST("get-user-rating")
    Observable<RateModel> getUserRate(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("refresh-token")
    Observable<PublicModel> refreshToken(@Field("user_id") String user_id, @Field("token") String token, @Field("device_type") String device_type);
    /*
     * */

    @FormUrlEncoded
    @POST("get-nearby-restaurants")
    Observable<NearbyRestaurantModel> getNearbyRestaurant(@Field("lat") String lat, @Field("lng") String lng, @Field("category") String categories);

    @FormUrlEncoded
    @POST("search-nearby-restaurants")
    Observable<NearbyRestaurantModel> getSearchNearbyRestaurant(@Field("lat") String lat, @Field("lng") String lng, @Field("search_query") String query);

    @FormUrlEncoded
    @POST("signup-as-restaurant-agent")
    Observable<SingupAsAgentModel> singupAsAgent(@Field("user_id") String userId, @Field("restaurant_id") String restaurant_id, @Field("restaurant_name") String restaurant_name);

    @FormUrlEncoded
    @POST("remove-me-as-agent")
    Observable<PublicModel> removeMeAsAgent(@Field("user_id") String user_id, @Field("place_id") String place_id);

    @FormUrlEncoded
    @POST("get-restaurant-agents")
    Observable<RestaurantAgentModel> getRestaurantAgent(@Field("restaurant_id") String resId);

    @FormUrlEncoded
    @POST("get-restaurant-orders")
    Observable<RestaurantOrdersModel> getRestaurantOrders(@Field("restaurant_id") String resID);

    @FormUrlEncoded
    @POST("make-an-order")
    Observable<MakeOrderModel> makeOrder(@FieldMap HashMap<String, String> map);//minute

    @FormUrlEncoded
    @POST("get-agent-available-orders")
    Observable<AganetAvailebleOrderModel> getAgentAvailableOrder(@Field("user_id") String userID);

    @FormUrlEncoded
    @POST("get-agent-confirmed-orders")
    Observable<AganetAvailebleOrderModel> getAgentConfirmedOrder(@Field("user_id") String userID);

    @FormUrlEncoded
    @POST("get-order")
    Observable<OrderModel> getOrder(@Field("order_id") String orderID);

    @FormUrlEncoded
    @POST("user-delete-order")
    Observable<PublicModel> deleteOrder(@Field("order_id") String orderID);

    @FormUrlEncoded
    @POST("get-stores-i-signed-in")
    Observable<MResModel> getMyRes(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("agent-accept-an-order")
    Observable<AgentAcceptedOrderModel> agentAcceptedOrder(@Field("order_id") String orderID, @Field("user_id") String user_id, @Field("delivery fee") String delivery_fee, @Field("order_price") String order_price,

                                                           @Field("total_price") String total_price, @Field("agent_lat") String agent_lat, @Field("agent_lng") String agent_lng);

    @FormUrlEncoded
    @POST("get-delivery-requests-for-an-order")
    Observable<PopupModel> getPopupModel(@Field("order_id") String orderID, @Field("delivery_request_id") String delivery_request_id);

    @FormUrlEncoded
    @POST("confirm-delivery-request")
    Observable<PublicModel> confirmDelievryRequest(@Field("order_id") String order_id, @Field("agent_id") String agentID);

    @FormUrlEncoded
    @POST("user-deny-delivery-request")
    Observable<PublicModel> rejectDelivaryRequest(@Field("notification_id") String notifyID, @Field("delivery_request_id") String deliveyID);

    @FormUrlEncoded
    @POST("get-user-orders")
    Observable<MyOrderModel> getMyOrder(@Field("user_id") String userID);

    @FormUrlEncoded
    @POST("get-agent-orders")
    Observable<MyOrderModel> getMyOrderAsAgent(@Field("user_id") String userID);

    @FormUrlEncoded
    @POST("get-user-active-orders")
    Observable<AganetAvailebleOrderModel> getUserActiveOrder(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("get-user-finished-orders")
    Observable<AganetAvailebleOrderModel> getUserFinishedOrder(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("agent-back-of-delivery-request")
    Observable<PublicModel> backOfOrder(@Field("request_id") String requestId);

    @FormUrlEncoded
    @POST("agent-change-order-status-to-delivered")
    Observable<PublicModel> doneOrder(@Field("order_id") String orderId);

    @FormUrlEncoded
    @POST("agent-change-order-bill")
    Observable<PublicModel> changeBill(@Field("order_id") String orderID, @Field("order_price") String order_price, @Field("delivery_fee") String delivery_fee);

    @FormUrlEncoded
    @POST("get-all-notifications")
    Observable<NotificationModel> getAllNotification(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("make-verification-request")
    Observable<PublicModel> activeAgent(@Field("user_id") String userId,
                                        @Field("name") String name,
                                        @Field("id_number") String id_number,
                                        @Field("country") String country,
                                        @Field("phone") String phone,
                                        @Field("email") String email,
                                        @Field("id_image") String id_image,
                                        @Field("driving_licence_image") String driving_licence_image,
                                        @Field("front_of_the_car_image") String front_of_the_car_image, @Field("back_of_the_car_image") String back_of_the_car_image);

    /*
    trood
    * */

    @GET("get-all-troods-companies")
    Observable<TroodsModel> getAllTroodsCompanies();

    @FormUrlEncoded
    @POST("get-single-troods-company")
    Observable<TroodDetailsModel> getCompany(@Field("company_id") String campnyId, @Field("logged_user_id") String userID);

    @FormUrlEncoded
    @POST("sign-up-as-trood-company-agent")
    Observable<PublicModel> singUpAsTroodAgent(@Field("company_id") String company_id, @Field("agent_id") String agent_id);

    @FormUrlEncoded
    @POST("remove-me-as-trood-company_agent")
    Observable<PublicModel> removeMeAsTroodAgent(@Field("company_id") String company_id, @Field("agent_id") String agent_id);

    @FormUrlEncoded
    @POST("get-troods-companies-for-agent")
    Observable<String> getTroodCompaniesForAgent(@Field("agent_id") String agent_id);


    @FormUrlEncoded
    @POST("get-all-agents-for-trood-company")
    Observable<AllAgetntForTroodModel> getAllAgentForTroodCompany(@Field("company_id") String campnyId);

    @FormUrlEncoded
    @POST("get-my-troods-orders-as-company")
    Observable<TroodAsCompanyModel> getOrdersAsTroodCompany(@Field("company_id") String id);

    @FormUrlEncoded
    @POST("make-trood-order")
    Observable<PublicModel> makeTroodOrder(@Field("user_id") String userId,
                                           @Field("user_id_image") String user_id_image,
                                           @Field("order_details") String order_details,
                                           @Field("company_id") String company_id,
                                           @Field("from_lat") String from_lat,
                                           @Field("from_lng") String from_lng,
                                           @Field("from_address") String from_address,
                                           @Field("to_lat") String to_lat,
                                           @Field("to_lng") String to_lng,
                                           @Field("to_address") String to_address,
                                           @Field("user_name") String userName,
                                           @Field("user_image") String user_image);

    @FormUrlEncoded
    @POST("get-trood-order")
    Observable<TroodOrderDetailsModel> getTroodOrderDetails(@Field("order_id") String orderID);


    @FormUrlEncoded
    @POST("agent-apply-to-deliver-tard")
    Observable<PublicModel> agentApplyToDeliverTard(@Field("agent_id") String agent_id,
                                                    @Field("agent_lat") String agent_lat,
                                                    @Field("agent_lng") String agent_lng,
                                                    @Field("total_price") String total_price,
                                                    @Field("order_id") String order_id);

    @FormUrlEncoded
    @POST("get-delivery-request-for-tard")
    Observable<DeliveryRequestModel> getDeliveryReqest(@Field("agent_id") String agent_id, @Field("order_id") String order_id);

    @FormUrlEncoded
    @POST("{path}")
    Observable<PublicModel> userActionTardRequest(@Path("path") String path, @Field("agent_id") String agentId, @Field("order_id") String order_id);

    @FormUrlEncoded
    @POST("get-my-troods-orders")
    Observable<MyOrderTroodModel> getMyTroodOrder(@Field("user_id") String userID);

    @FormUrlEncoded
    @POST("get-my-troods-orders-as-agent")
    Observable<MyOrderTroodModel> getMyTroodOrderAsAgent(@Field("user_id") String userID);

    @FormUrlEncoded
    @POST("change-order-status")
    Observable<PublicModel> changeStatusTrood(@Field("order_id") String order_id, @Field("status") String status);

    @FormUrlEncoded
    @POST("company-assign-trood-agent")
    Observable<PublicModel> companyRequetAgentToDeliverdTrood(@Field("agent_id") String agent_id,
                                                              @Field("agent_lat") String agent_lat,
                                                              @Field("agent_lng") String agent_lng,
                                                              @Field("company_id") String company_id,
                                                              @Field("order_id") String order_id,
                                                              @Field("total_price") String total_price);

    @FormUrlEncoded
    @POST("get-assignment-request-info")
    Observable<AssigmentInfoModel> getAssignmentInfo(@Field("agent_id") String agent_id, @Field("order_id") String order_id);

    @FormUrlEncoded
    @POST("agent-confirm-company-assignment")
    Observable<PublicModel> agentConfrmCompanyRequest(@Path("path") String path, @Field("agent_id") String agent_id, @Field("order_id") String order_id);

    /**
     *
     */

    @GET("get-pay-info")
    Observable<PayInfoModel> getPayInfo();

    @FormUrlEncoded
    @POST("make-transfer")
    Observable<PublicModel> makeTrasfer(@Field("user_id") String userId, @Field("name") String name, @Field("phone") String phone, @Field("bank_name") String bankName, @Field("bank_account_number") String bank_account_number, @Field("transfer_number") String transfer_number);

    @FormUrlEncoded
    @POST("user-change-nearby-notification-status")
    Observable<ChangeNotificationModel> changeNearbyNotificationStatus(@Field("user_id") String userID);

    @FormUrlEncoded
    @POST("user-change-notification-status")
    Observable<ChangeNotificationModel> changeNotificationState(@Field("user_id") String userID);

    @GET("get-terms-and-conditions")
    Observable<TermsModel> getTerm();

    @GET("about-ma5dom")
    Observable<AboutModel> getAbout();

    @GET("get-app-rating-link")
    Observable<RatingAppLinkModel> ratingAppLink();

    @GET("maps/api/directions/json?mode=driving&sensor=false")
    Observable<List<List<HashMap<String, String>>>> getRoute(@Query("origin") String origin, @Query("destination") String destination);
}
