# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\gsma\AndroidStudioSDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#项目模型目录#

#butterknife#
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {@butterknife.* <fields>;}
-keepclasseswithmembernames class * {@butterknife.* <methods>;}

#eventbus######
-keep class de.greenrobot.event.eventbus.** { *; }
-keepclassmembers class ** {public void onEvent*(**);}

#systembartint系统栏
-keep class com.readystatesoftware.systembartint.** { *; }

#fastjson#
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** {*;}
-keep class com.baidu.** { *; }#这个可能需要#
-keep class vi.com.gdi.bgl.android.**{*;}

#okhttp  okio
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *;}
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**


#通用#
-optimizationpasses 5
-verbose
-dontnote
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-dontwarn
-keepattributes InnerClasses,Signature,Exceptions
-allowaccessmodification

#for android begin#
#Keep native methods JNI#
-keepclassmembers class * {native <methods>;}
# 如果你使用了support v4包，请添加如下混淆代码
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.app.View
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class **.R$* {
    *;
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#for android end#
# for JavascriptInterface beging #
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

#删除Log代码
#public static *** e(...);
-assumenosideeffects class android.util.Log {
    public static *** w(...);
    public static *** wtf(...);
    public static *** d(...);
    public static *** v(...);
}