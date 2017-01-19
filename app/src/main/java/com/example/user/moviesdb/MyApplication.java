package com.example.user.moviesdb;



        import android.app.Application;
        import android.content.pm.PackageInfo;
        import android.content.pm.PackageManager;
        import android.content.pm.Signature;
        import android.util.Base64;
        import android.util.Log;

        import java.security.MessageDigest;
        import java.security.NoSuchAlgorithmException;

/**
 * Created by Belal on 5/3/2015.
 */
public class MyApplication extends Application {

    private final static String TAG = "Key";
    @Override
    public void onCreate() {
        super.onCreate();
        printHashKey();
    }

    public void printHashKey(){
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.user.moviesdb",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.d(TAG , "KeyHash:");
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
