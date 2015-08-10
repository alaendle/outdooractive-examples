# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Tools\android-sdk/tools/proguard/proguard-android.txt
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

## Copied from Android SDK Plugin for SBT - https://github.com/pfn/android-sdk-plugin/blob/master/resources/android-proguard.config

# for debugging, don't inline methods
#-dontoptimize
#-optimizationpasses 5

-dontobfuscate
-dontoptimize
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-verbose
-flattenpackagehierarchy

###
# Android config
###
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify

-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*,!code/allocation/variable
#-optimizationpasses 5
#-allowaccessmodification

-keepattributes *Annotation*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {
    native <methods>;
}

# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-dontnote android.annotation.**
-dontnote com.android.vending.licensing.**
-dontnote com.google.vending.licensing.**
-dontwarn android.support.**
-dontnote android.support.**

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

###
# Scala proguard config
###
-dontnote scala.ScalaObject
-dontnote org.xml.sax.EntityResolver
-dontnote scala.concurrent.forkjoin.**
-dontwarn scala.beans.ScalaBeanInfo
-dontwarn scala.concurrent.**
-dontnote scala.reflect.**
-dontwarn scala.reflect.**
-dontwarn scala.sys.process.package$

-dontwarn **$$anonfun$*
-dontwarn scala.collection.immutable.RedBlack$Empty
-dontwarn scala.tools.**,plugintemplate.**

-keep public class scala.reflect.ScalaSignature
# This is gone in 2.11
-keep public interface scala.ScalaObject

-keepclassmembers class * {
    ** MODULE$;
}

-keep class scala.collection.SeqLike {
    public java.lang.String toString();
}

-keepclassmembernames class scala.concurrent.forkjoin.ForkJoinPool {
    long eventCount;
    int  workerCounts;
    int  runControl;
    scala.concurrent.forkjoin.ForkJoinPool$WaitQueueNode syncStack;
    scala.concurrent.forkjoin.ForkJoinPool$WaitQueueNode spareStack;
}

-keepclassmembernames class scala.concurrent.forkjoin.ForkJoinWorkerThread {
    int base;
    int sp;
    int runState;
}

-keepclassmembernames class scala.concurrent.forkjoin.ForkJoinTask {
    int status;
}

-keepclassmembernames class scala.concurrent.forkjoin.LinkedTransferQueue {
    scala.concurrent.forkjoin.LinkedTransferQueue$PaddedAtomicReference head;
    scala.concurrent.forkjoin.LinkedTransferQueue$PaddedAtomicReference tail;
    scala.concurrent.forkjoin.LinkedTransferQueue$PaddedAtomicReference cleanMe;
}

## Project specific settings

-keep class * extends java.util.ListResourceBundle { protected Object[][] getContents(); }
-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable { public static final *** NULL; }
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * { @com.google.android.gms.common.annotation.KeepName *; }
-keepnames class * implements android.os.Parcelable { public static final ** CREATOR; }
-dontwarn javax.inject.Named