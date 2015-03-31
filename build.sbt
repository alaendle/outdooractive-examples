scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.google.android.gms" % "play-services" % "6.5.87",
  aar("org.macroid" %% "macroid" % "2.0.0-M3"),
  "org.json4s" %% "json4s-native" % "3.2.11"
)

proguardCache in Android += ProguardCache("org.json4s") % "org.json4s" %% "json4s-core"

proguardOptions in Android ++= Seq(
  "-keep class * extends java.util.ListResourceBundle { protected Object[][] getContents(); }",
  "-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable { public static final *** NULL; }",
  "-keepnames @com.google.android.gms.common.annotation.KeepName class *",
  "-keepclassmembernames class * { @com.google.android.gms.common.annotation.KeepName *; }",
  "-keepnames class * implements android.os.Parcelable { public static final ** CREATOR; }",
  "-dontwarn javax.inject.Named"
)

dexMaxHeap in Android := "1024m"

apkSigningConfig in Android := Option(android.PlainSigningConfig(keystore = file("key.store"), storePass = sys.env("storePass"), alias = "outdooractive"))
