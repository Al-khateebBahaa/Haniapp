package com.ahmedMustafa.Hani.di;

import com.ahmedMustafa.Hani.activity.AllResActivity;
import com.ahmedMustafa.Hani.activity.BankTransferActivity;
import com.ahmedMustafa.Hani.activity.auth.EntroActivity;
import com.ahmedMustafa.Hani.activity.auth.RegisterActivity;
import com.ahmedMustafa.Hani.activity.orders.ChangeBillActivity;
import com.ahmedMustafa.Hani.activity.orders.ChatActivity;
import com.ahmedMustafa.Hani.activity.CommentsActivity;
import com.ahmedMustafa.Hani.activity.SettingActivity;
import com.ahmedMustafa.Hani.activity.auth.SplashActivity;
import com.ahmedMustafa.Hani.activity.auth.LoginActivity;
import com.ahmedMustafa.Hani.activity.MainActivity;
import com.ahmedMustafa.Hani.activity.auth.SelectLocationActivity;
import com.ahmedMustafa.Hani.activity.auth.SendPhoneNumberActivity;
import com.ahmedMustafa.Hani.activity.auth.VerficationActivity;
import com.ahmedMustafa.Hani.activity.orders.DelivaryRequestActivity;
import com.ahmedMustafa.Hani.activity.orders.NewOrderActivity;
import com.ahmedMustafa.Hani.activity.orders.RequestOrderConnectionActivity;
import com.ahmedMustafa.Hani.activity.orders.RestaurantDetailsActivity;
import com.ahmedMustafa.Hani.activity.settingPages.AboutAppActivity;
import com.ahmedMustafa.Hani.activity.settingPages.EditProfileActivity;
import com.ahmedMustafa.Hani.activity.settingPages.HowWorkActivity;
import com.ahmedMustafa.Hani.activity.settingPages.MyResActivity;
import com.ahmedMustafa.Hani.activity.settingPages.PlociyActivity;
import com.ahmedMustafa.Hani.activity.settingPages.TermsActivity;
import com.ahmedMustafa.Hani.activity.trood.MakeTroodOrderActivity;
import com.ahmedMustafa.Hani.activity.trood.RequestAgentActivity;
import com.ahmedMustafa.Hani.activity.trood.TroodDetailsActivity;
import com.ahmedMustafa.Hani.di.modules.AllResModule;
import com.ahmedMustafa.Hani.di.modules.ChangeBillModule;
import com.ahmedMustafa.Hani.di.modules.ChatModule;
import com.ahmedMustafa.Hani.di.modules.CommentModule;
import com.ahmedMustafa.Hani.di.modules.EntorModule;
import com.ahmedMustafa.Hani.di.modules.LoginModule;
import com.ahmedMustafa.Hani.di.modules.MainModule;
import com.ahmedMustafa.Hani.di.modules.NewOrderModule;
import com.ahmedMustafa.Hani.di.modules.RequestOrderConnectingModule;
import com.ahmedMustafa.Hani.di.modules.ResDetailsModule;
import com.ahmedMustafa.Hani.di.modules.SelectLocationModule;
import com.ahmedMustafa.Hani.di.modules.SendPhoneModule;
import com.ahmedMustafa.Hani.di.modules.TroodDetailsModule;
import com.ahmedMustafa.Hani.di.modules.VerficationModule;
import com.ahmedMustafa.Hani.di.modules.fragment.ProvideMainModule;
import com.ahmedMustafa.Hani.di.modules.fragment.ProvideResModule;
import com.ahmedMustafa.Hani.di.modules.fragment.ProvideTroodMdules;
import com.ahmedMustafa.Hani.di.modules.settingModules.AboutAppModule;
import com.ahmedMustafa.Hani.di.modules.settingModules.EditProfileModule;
import com.ahmedMustafa.Hani.di.modules.settingModules.HowWorkModule;
import com.ahmedMustafa.Hani.di.modules.settingModules.MyResModule;
import com.ahmedMustafa.Hani.di.modules.settingModules.PolicyModule;
import com.ahmedMustafa.Hani.di.modules.settingModules.SettingModule;
import com.ahmedMustafa.Hani.di.modules.settingModules.TermsModule;
import com.ahmedMustafa.Hani.model.ChatModel;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {LoginModule.class})
    abstract SplashActivity SplashActivity();

    @ContributesAndroidInjector(modules = {LoginModule.class})
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(modules = {LoginModule.class})
    abstract RegisterActivity RegisterActivity();


    @ContributesAndroidInjector(modules = {EntorModule.class})
    abstract EntroActivity EntroActivity();

    @ContributesAndroidInjector(modules = {VerficationModule.class})
    abstract VerficationActivity VerficationActivity();

    @ContributesAndroidInjector(modules = {MainModule.class, ProvideMainModule.class})
    abstract MainActivity MainActivity();

    @ContributesAndroidInjector(modules = {AllResModule.class})
    abstract AllResActivity AllResActivity();

    @ContributesAndroidInjector(modules = {ResDetailsModule.class, ProvideResModule.class})
    abstract RestaurantDetailsActivity RestaurantDetailsActivity();

    @ContributesAndroidInjector(modules = {SendPhoneModule.class})
    abstract SendPhoneNumberActivity SendPhoneNumberActivity();

    @ContributesAndroidInjector(modules = {NewOrderModule.class})
    abstract NewOrderActivity NewOrderActivity();

    @ContributesAndroidInjector(modules = {SelectLocationModule.class})
    abstract SelectLocationActivity SelectLocationActivity();

    @ContributesAndroidInjector(modules = {RequestOrderConnectingModule.class})
    abstract RequestOrderConnectionActivity RequestOrderConnectionActivity();

    @ContributesAndroidInjector(modules = {RequestOrderConnectingModule.class})
    abstract DelivaryRequestActivity DelivaryRequestActivity();

    @ContributesAndroidInjector(modules = {ChatModule.class})
    abstract ChatActivity ChatActivity();

    @ContributesAndroidInjector(modules = {ChangeBillModule.class})
    abstract ChangeBillActivity ChangeBillActivity();

    @ContributesAndroidInjector(modules = {CommentModule.class})
    abstract CommentsActivity CommentsActivity();

    @ContributesAndroidInjector(modules = {ChatModule.class})
    abstract BankTransferActivity BankTransferActivity();

    @ContributesAndroidInjector(modules = {TroodDetailsModule.class, ProvideTroodMdules.class})
    abstract TroodDetailsActivity TroodDetailsActivity();

    @ContributesAndroidInjector(modules = {ChatModule.class})
    abstract MakeTroodOrderActivity MakeTroodOrderActivity();

    @ContributesAndroidInjector(modules = {ChatModule.class})
    abstract RequestAgentActivity RequestAgentActivity();


    // setting Section
    @ContributesAndroidInjector(modules = {SettingModule.class})
    abstract SettingActivity SettingActivity();

    @ContributesAndroidInjector(modules = {AboutAppModule.class})
    abstract AboutAppActivity AboutAppActivity();

    @ContributesAndroidInjector(modules = {EditProfileModule.class})
    abstract EditProfileActivity EditProfileActivity();

    @ContributesAndroidInjector(modules = {HowWorkModule.class})
    abstract HowWorkActivity HowWorkActivity();

    @ContributesAndroidInjector(modules = {MyResModule.class})
    abstract MyResActivity MyResActivity();

    @ContributesAndroidInjector(modules = {PolicyModule.class})
    abstract PlociyActivity PlociyActivity();

    @ContributesAndroidInjector(modules = {TermsModule.class})
    abstract TermsActivity TermsActivity();


}
