package com.example.bikeservice;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment  {
    private ImageView back,profilepic;
    public static Context context;
    private Button save,edit;
    private TextView mail,name,usn,phone,
            heademail,headname,regnx,passcode,pass,acyear;
    private EditText year,phonex,newpasscode;
    private UploadTask uploadTask;
    private static final String TAG ="ProfileActivity";
    private static final String keyEmail="mail";
    private static final String keyName="name";
    private static final String keyMobile="mobile";

    private static final String keypassword="password";
    private static final String keyregn="regn";


    private FirebaseAuth mAuth;
    private static final String key_pplink="PPLink1";
    private StorageReference  mStorageRef = FirebaseStorage.getInstance().getReference("Images");
    private Uri imageUri;
    String usnx;
    String imageurl;
    ProgressDialog progressDialog;
    String mailgetx;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        mailgetx=getActivity().getIntent().getStringExtra("mail");
        Log.d("wwwwww",mailgetx);

        profilepic=(ImageView)getActivity().findViewById(R.id.userpic);
        heademail=(TextView)getActivity().findViewById(R.id.heademail);
        headname=(TextView)getActivity().findViewById(R.id.headename);
        mail=(TextView)getActivity().findViewById(R.id.mail);
        name=(TextView)getActivity().findViewById(R.id.name);
        pass=(TextView)getActivity().findViewById(R.id.password);


        phone=(TextView)getActivity().findViewById(R.id.mobno);
        phonex=(EditText)getActivity().findViewById(R.id.phonex);

        edit=(Button)getActivity().findViewById(R.id.edit);
        save=(Button)getActivity().findViewById(R.id.save);
        profilepic.setOnClickListener(v -> selectImage());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save.setVisibility(View.VISIBLE);
                phonex.setVisibility(View.VISIBLE);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save.setVisibility(View.GONE);
                phonex.setVisibility(View.GONE);


                String newphone=phonex.getText().toString();


                if (newphone.isEmpty() || newphone.length()<10)
                {
                    phonex.setError("Enter proper phone no.");
                    phonex.requestFocus();
                    return;
                }

                String mailget = getActivity().getIntent().getStringExtra("mail");

                 DocumentReference noteref=db.collection(mailget).document("profile");

                noteref.update(keyMobile,newphone);

                Toast.makeText(getContext(),"Profile updated sucessfully",Toast.LENGTH_SHORT).show();
                noteref.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists())
                    {
                        String emailx=documentSnapshot.getString(keyEmail);
                        String namex=documentSnapshot.getString(keyName);
                        String mobilex=documentSnapshot.getString(keyMobile);
                        String passwordx=documentSnapshot.getString(keypassword);
                        mail.setText(emailx);
                        name.setText(namex);
                        phone.setText(mobilex);
                        pass.setText(passwordx);


                        heademail.setText(emailx);
                        headname.setText(namex);

                    }
                    else {
                        Toast.makeText(getContext(),"Document does not exist!",Toast.LENGTH_SHORT).show();

                    }

                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(),"Document does not exist!",Toast.LENGTH_SHORT).show();
                                Log.d(TAG,e.toString());
                            }
                        });
            }
        });
        String mailget=getActivity().getIntent().getStringExtra("mail");
        DocumentReference noteref=db.collection(mailget).document("profile");
        noteref.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists())
            {
                String emailx=documentSnapshot.getString(keyEmail);
                String namex=documentSnapshot.getString(keyName);
                String mobilex=documentSnapshot.getString(keyMobile);

                String passwordx=documentSnapshot.getString(keypassword);

                String pplink1=documentSnapshot.getString(key_pplink);

                mail.setText(emailx);
                name.setText(namex);
                phone.setText(mobilex);


                heademail.setText(emailx);
                headname.setText(namex);

                 pass.setText(passwordx);
                if (pplink1.isEmpty())
                {
                    Toast.makeText(context,"Upload a profile pic.",Toast.LENGTH_SHORT).show();
                }else {


                    //String imageUri = "https://i.imgur.com/tGbaZCY.jpg";
                    // ImageView ivBasicImage = (ImageView) findViewById(R.id.ivBasicImage);
                    Glide.with(context).load(pplink1).into(profilepic);
                }


            }
            else {
                Toast.makeText(getContext(),"Document does not exist!",Toast.LENGTH_SHORT).show();

            }

        })
                .addOnFailureListener(e -> {
                    Toast.makeText(context,"Document does not exist!",Toast.LENGTH_SHORT).show();
                    Log.d(TAG,e.toString());
                });

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         return inflater.inflate(R.layout.fragment_profile, container, false);
      //  View view = inflater.inflate(R.layout.fragment_home, container, false);





    }


    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new  Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    profilepic.setImageBitmap(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                profilepic.setImageURI(selectedImage);
                final StorageReference riversRef = mStorageRef.child(mailgetx);

                try {
                    uploadTask =  riversRef.putFile(selectedImage);
                }
                catch (Exception e)
                { }

                progressDialog=new ProgressDialog(getContext());
                progressDialog.setMax(100);
                progressDialog.setMessage("Uploading...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.show();
                progressDialog.setCancelable(false);

                uploadTask.addOnProgressListener(taskSnapshot -> {
                    double progress=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    progressDialog.incrementProgressBy((int) progress);
                });
                uploadTask.addOnSuccessListener(taskSnapshot -> progressDialog.dismiss());
                uploadTask.addOnFailureListener(e -> progressDialog.dismiss());

                Task<Uri> uriTask=uploadTask.continueWithTask(task -> {
                    if (!task.isSuccessful())
                    {

                        throw task.getException();
                    }
                    return riversRef.getDownloadUrl();
                }).addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                    {
                        Uri durl=task.getResult();
                        String link1=durl.toString();

                        String mailget=getActivity().getIntent().getStringExtra("mail");
                        DocumentReference noteref=db.collection(mailget).document("profile");
                        noteref.update(key_pplink,link1);
                        Toast.makeText(getContext(), "Uploaded sucessfully",Toast.LENGTH_SHORT).show();

                    }

                });


            }
        }
    }
}