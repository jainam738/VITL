package com.appleto.Vitl.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.appleto.Vitl.R;
import com.appleto.Vitl.model.GetCategoryResponse;
import com.appleto.Vitl.retrofit2.ApiClient;
import com.appleto.Vitl.retrofit2.ApiInterface;
import com.appleto.Vitl.utills.Storage;
import com.appleto.Vitl.utills.Utils;
import com.google.gson.JsonObject;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class VendorListSpecialActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;

    private Storage storage;
    ImageView iv_back, img_item;
    TextView tv_add_attachment;
    EditText edt_phoneNo, edt_itemname, edt_discription, edt_originalprice, edt_discountedprice, edt_enddate, edt_qty;
    Button btn_submit;
    Spinner spCatList, spUnitList;
    private static int RESULT_LOAD_IMAGE = 1;
    private Uri itemImageUri, itemImageOriginalUri;

    String[] units = {"Units", "p/Kg", "p/Ltr", "p/grm", "each"};

    String picturePath, selCatId = "0";
    List<GetCategoryResponse.Datum> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_list_special);

        context = VendorListSpecialActivity.this;
        storage = new Storage(context);

        tv_add_attachment = findViewById(R.id.tv_add_attachment);
        img_item = findViewById(R.id.img_item);
        iv_back = findViewById(R.id.iv_back);
        edt_phoneNo = findViewById(R.id.edt_phone_no);
        edt_itemname = findViewById(R.id.edt_itemname);
        edt_discription = findViewById(R.id.edt_discription);
        edt_originalprice = findViewById(R.id.edt_originalprice);
        edt_discountedprice = findViewById(R.id.edt_discountedprice);
        edt_enddate = findViewById(R.id.edt_enddate);
        btn_submit = findViewById(R.id.btn_submit);
        spCatList = findViewById(R.id.spin_categoy);
        edt_qty = findViewById(R.id.edt_qty);

        spUnitList = findViewById(R.id.spin_unit);
        final ArrayAdapter unit = new ArrayAdapter(context, R.layout.spinner_dropdown, units);
        unit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUnitList.setAdapter(unit);

//        getcategory();

        edt_enddate.setOnClickListener(this);
        tv_add_attachment.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        iv_back.setOnClickListener(this);

        spCatList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = spCatList.getSelectedItem().toString();
                if (categoryList.size() > 0) {
                    for (int j = 0; j < categoryList.size(); j++) {
                        if (categoryList.get(i).getCategoryName().equalsIgnoreCase(selectedItem)) {
                            selCatId = categoryList.get(i).getCategoryId();
                            break;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getcategory() {
        Utils.progress_show(context);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService
                .getcategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetCategoryResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GetCategoryResponse getCategoryResponse) {
                        if (getCategoryResponse.getStatus() == 1) {
                            if (getCategoryResponse.getData().size() > 0) {
                                categoryList = getCategoryResponse.getData();
                                ArrayAdapter aa = new ArrayAdapter(context, R.layout.spinner_dropdown, R.id.text_spinner, getCategoryResponse.getData());
                                //aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                aa.setDropDownViewResource(R.layout.spinner_dropdown);
                                spCatList.setAdapter(aa);
                            }
                        } else {
                            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                        }
                        Utils.progress_dismiss(context);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.progress_dismiss(context);
                        Toast.makeText(context, R.string.connection_timeout, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                img_item.setImageURI(result.getUri());
                itemImageUri = result.getUri();
                itemImageOriginalUri = result.getOriginalUri();
            }
        }
//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            picturePath = cursor.getString(columnIndex);
//            cursor.close();
//
//            img_item.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//
//        }
    }

    private void additem(String itemname, String description, String originalprice, String discountprice, String picturePath, String unit, String enddate, String quantity) {

        MultipartBody.Part imagenPerfil = null;

        if (itemImageUri != null) {
            imagenPerfil = Utils.createMultipart(context, itemImageUri, itemImageOriginalUri, "item_image");
            /*File file = new File(picturePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            imagenPerfil = MultipartBody.Part.createFormData("item_image", file.getName(), requestFile);*/
        }

        Utils.progress_show(context);

        RequestBody vendorid = Utils.createPart(context, storage.getVenderId());
        RequestBody additemname = Utils.createPart(context, itemname);
        RequestBody itemdescription = Utils.createPart(context, description);
        RequestBody itemoriginalprice = Utils.createPart(context, originalprice);
        RequestBody itemdiscountprice = Utils.createPart(context, discountprice);
        RequestBody type = Utils.createPart(context, "specials");
        RequestBody itemcategoryid = Utils.createPart(context, selCatId);
        RequestBody unitid = Utils.createPart(context, unit);
        RequestBody enddates = Utils.createPart(context, enddate);
        RequestBody rb_quantity = Utils.createPart(context, quantity);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService
                .additem(vendorid, additemname, itemdescription, itemoriginalprice, itemdiscountprice, imagenPerfil, type, itemcategoryid, unitid, enddates, rb_quantity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JsonObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onSuccess(JsonObject response) {
                        if (response.get("status").getAsInt() == 1) {

                            Toast.makeText(context, response.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            finish();

                        } else {
                            Toast.makeText(context, response.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                        }

                        Utils.progress_dismiss(context);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.progress_dismiss(context);
                        Toast.makeText(context, R.string.connection_timeout, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onClick(View view) {
        if (view == iv_back) {
            finish();
        } else if (view == tv_add_attachment) {
            Utils.openGallery(context);
            /*if (ActivityCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            } else {
                ActivityCompat.requestPermissions(VendorListSpecialActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
            }*/
        } else if (view == edt_enddate) {
            Calendar mcurrentDate = Calendar.getInstance();
            final int[] mYear = {mcurrentDate.get(Calendar.YEAR)};
            final int[] mMonth = {mcurrentDate.get(Calendar.MONTH)};
            final int[] mDay = {mcurrentDate.get(Calendar.DAY_OF_MONTH)};

            DatePickerDialog mDatePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                    Calendar myCalendar = Calendar.getInstance();
                    myCalendar.set(Calendar.YEAR, selectedyear);
                    myCalendar.set(Calendar.MONTH, selectedmonth);
                    myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                    String myFormat = "dd/MM/yy"; //Change as you need
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                    edt_enddate.setText(sdf.format(myCalendar.getTime()));

                    mDay[0] = selectedday;
                    mMonth[0] = selectedmonth;
                    mYear[0] = selectedyear;
                }
            }, mYear[0], mMonth[0], mDay[0]);
            //mDatePicker.setTitle("Select date");
            mDatePicker.show();
        } else if (view == btn_submit) {
            String itemname = edt_itemname.getText().toString().trim();
            String discription = edt_discription.getText().toString().trim();
            String originalprice = edt_originalprice.getText().toString().trim();
            String discountedprice = edt_discountedprice.getText().toString().trim();
            String enddate = edt_enddate.getText().toString().trim();

            String unit = spUnitList.getSelectedItem().toString();

            String q = edt_qty.getText().toString().trim();

            additem(itemname, discription, originalprice, discountedprice, picturePath, unit, enddate, q);
        }
    }
}
