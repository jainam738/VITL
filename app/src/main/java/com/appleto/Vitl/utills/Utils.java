package com.appleto.Vitl.utills;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.appleto.Vitl.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by simon on 01-04-2019.
 */

public class Utils {

    public static Dialog dialog;
    private Context context;
    private static int randomCount = 10;
    public static Toast toast;


    public Utils(Context context) {
        this.context = context;
    }

    @SuppressLint("NewApi")
    public static void progress_show(Context context) {
        if(dialog == null) {
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.progress_dialog);
            dialog.setCancelable(true);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.show();
        }
    }

    public static void progress_dismiss(Context context) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public static void showToast(Context ctx, String message) {
        cancelToast();
        toast = Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void cancelToast() {
        if (toast != null && toast.getView().isShown()) {
            toast.cancel();
        }
    }

    public static int generateRandom(int lastRandomNumber) {
        Random random = new Random();
        int rotate = 1 + random.nextInt(randomCount - 1);
        return (lastRandomNumber + rotate) % randomCount;
    }

    public static long getMillisecondsFromMinutes(int second) {
        //return (min * 60) * 1000;
        return second * 1000;
    }

    public static HashMap<String, Integer> getTimeFromMillisecond(long millis) {

        HashMap<String, Integer> holder = new HashMap<>();
        int days = (int) TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        int hours = (int) TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(millis);

        holder.put("day", days);
        holder.put("hour", hours);
        holder.put("min", minutes);
        holder.put("sec", seconds);
        return holder;
    }


    public static String getPath(Context context, Uri _uri) {
        String filePath = "";
        //Uri _uri = data.getData();
        Log.d("", "URI = " + _uri);
        if (_uri != null && "content".equals(_uri.getScheme())) {
            Cursor cursor = context.getContentResolver().query(_uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            cursor.moveToFirst();
            filePath = cursor.getString(0);
            cursor.close();
        } else {
            filePath = _uri.getPath();
        }
        Log.d("", "Chosen path = " + filePath);
        return filePath;
    }

    public static String getFileName(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    public static boolean isEmailCorrect(EditText edt) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!edt.getText().toString().trim().matches(emailPattern)) {
            edt.setError("Please enter correct email");
            edt.requestFocus();
            return true;
        }
        return false;
    }

    public static RequestBody getJsonFromJsonArray(JSONArray jsonObject) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        return body;
    }

    public static RequestBody getJsonFromJsonObject(JSONObject jsonObject) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        return body;
    }

    public static void navigateTo(Activity curr_act, Class gotoClass) {
        Intent intent = new Intent(curr_act, gotoClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        curr_act.startActivity(intent);
    }

    public static int getWidthOfScreen(Context mContext) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        try {
            ((Activity) mContext).getWindowManager()
                    .getDefaultDisplay()
                    .getMetrics(displayMetrics);
        } catch (Exception e) {
            WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics.widthPixels;
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }

    public static void replaceFragment(Fragment fragment, String Tag,
                                       boolean isBackStack, boolean isMenuSupport,
                                       FragmentManager fragmentManager, int id) {
        FragmentManager fm = fragmentManager;
        FragmentTransaction ft = fm.beginTransaction();
        fragment.setHasOptionsMenu(isMenuSupport);
        ft.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        ft.replace(id, fragment, Tag);
        if (isBackStack) {
            ft.addToBackStack(Tag);
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    public static void openGallery(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            } else {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropWindowSize(600, 600)
                        .setMaxZoom(1)
                        .start((Activity) context);
            }
        } else {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setMinCropWindowSize(600, 600)
                    .setMaxZoom(1)
                    .start((Activity) context);
        }
    }

    public static void showDatePicker(Context context, final EditText ed) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String strDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        ed.setText(formatedDate(strDate));
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public static String formatedDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        String validDate = "";
        try {
            validDate = sdf.format(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return validDate;
    }

    public static RequestBody createPart(Context context, String ed) {
        return RequestBody.create(MultipartBody.FORM, ed);
    }

    @SuppressLint("NewApi")
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static MultipartBody.Part createMultipart(Context context, Uri profileUri, Uri profileOriginalUri, String partName) {
        MultipartBody.Part body;

        if (profileUri != null) {
            Utils utils = new Utils(context);

            String imageurl = utils.getPath(profileUri);
            File file = null;
            try {
                file = new File(imageurl);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (profileOriginalUri.toString().startsWith("file://")) {
                profileOriginalUri = getImageContentUri(context, file);
            }

            // create RequestBody instance from file
            RequestBody requestFile = RequestBody.create(
                            MediaType.parse(context.getContentResolver().getType(profileOriginalUri)), file);
            body = MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
        } else {
            body = null;
        }
        return body;
    }

    public String getPath(Uri _uri) {
        String filePath = "";
        //Uri _uri = data.getData();
        Log.d("", "URI = " + _uri);
        if (_uri != null && "content".equals(_uri.getScheme())) {
            Cursor cursor = context.getContentResolver().query(_uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            cursor.moveToFirst();
            filePath = cursor.getString(0);
            cursor.close();
        } else {
            filePath = _uri.getPath();
        }
        Log.d("", "Chosen path = " + filePath);
        return filePath;
    }

    public static boolean isInternetAvaiable(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) {
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }

    public static String getAddress(Context context, double lat, double lng) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());
        String address = "";
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses.size() > 0) {
                address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                /*String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();*/
            }
            return address;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    public static String getCountryState(Context context, double lat, double lng) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());
        String address = "";
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses.size() > 0) {
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                address = state +","+ country +","+ postalCode;
            }
            return address;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
