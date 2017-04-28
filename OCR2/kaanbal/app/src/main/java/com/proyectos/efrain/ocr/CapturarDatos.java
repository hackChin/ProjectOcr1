package com.proyectos.efrain.ocr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.proyectos.efrain.ocr.Util.Adaptador;
import com.proyectos.efrain.ocr.Util.MyTessOCR;
import com.proyectos.efrain.ocr.Util.Usuarios;
import com.proyectos.efrain.ocr.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class CapturarDatos extends AppCompatActivity {

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private static String MEDIA_DIRECTORY = "ImgExamenes";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;
    private String mPath;
    private MyTessOCR mTessOCR;
    Button btncalificar;
    Activity activity;
    Context contexto;
    ArrayList<Usuarios> array= new ArrayList<Usuarios>();
    Usuarios oDatos;
    Adaptador oAdaptador;
    ListView listaDatos;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capturar_datos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //mTessOCR = new MyTessOCR(this,"spa");
         btncalificar =(Button) findViewById(R.id.button4);


        btncalificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(CapturarDatos.this,"Ola",Toast.LENGTH_LONG).show();
                //doOpenFlor();
                Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.compartir);
                ImageView vista = (ImageView)findViewById(R.id.vista);

                vista.setImageBitmap(bitmap);
                //Reconocimeinto(bitmap);

            }
        });
        sharedPreferences=getSharedPreferences("Pictures", Context.MODE_PRIVATE);

        if(!TextUtils.isEmpty(Util.PicturePatch(sharedPreferences))){
            Bitmap bitmap = BitmapFactory.decodeFile(Util.PicturePatch(sharedPreferences));
            ImageView vista = (ImageView)findViewById(R.id.vista);
            vista.setImageBitmap(bitmap);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CapturarDatos.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_camara,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.menu_registros){

        }else if(id==R.id.menu_cargarfoto){
            OpenGallery();
        }else if(id==R.id.menu_tomarfoto){
            openCamera();
        }
        return super.onOptionsItemSelected(item);
    }



    private void OpenGallery()
    {

        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
       /*
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);*/
    }

    private void OpenCamera()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this,
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> Uri = " + uri);
                                }
                            });


                    Bitmap bitmap = BitmapFactory.decodeFile(mPath);

                    ImageView vista = (ImageView)findViewById(R.id.vista);
                    SavePictures(mPath);
                    vista.setImageBitmap(bitmap);
                    Reconocimeinto(bitmap);
                    break;
                case SELECT_PICTURE:
                    onSelectFromGalleryResult(data);
                    break;

            }
        }
    }
/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA){
                MediaScannerConnection.scanFile(this,
                        new String[]{mPath}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("ExternalStorage", "Scanned " + path + ":");
                                Log.i("ExternalStorage", "-> Uri = " + uri);
                            }
                        });


                Bitmap bitmap = BitmapFactory.decodeFile(mPath);

                ImageView vista = (ImageView)findViewById(R.id.vista);
                vista.setImageBitmap(bitmap);
            }
               //onCaptureImageResult(data);
        }
    }

    */

    private String onCaptureImageResult(Intent data) {
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis()+ ".jpg");
        String imagePath="";
        FileOutputStream fo;
        try {

            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());


            //Ruta de imagen
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 10;
            imagePath = destination.getAbsolutePath();
            //ruta de imagen

            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageView vista = (ImageView)findViewById(R.id.vista);
        vista.setImageBitmap(bitmap);
        return imagePath;
    }

    private  Bitmap DecodeImage(String photoPath){
        //BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        Bitmap bitmap = BitmapFactory.decodeFile(photoPath);

        return bitmap;
    }

    private  void Dialogo(final Bitmap bitmap){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Prosesar Imagen");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Reconocimeinto(bitmap);
                        ImageView vista = (ImageView)findViewById(R.id.vista);
                        vista.setImageBitmap(bitmap);
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    private void onSelectFromGalleryResult(Intent data) {
//String lenguaje="spa";
       // mTessOCR = new MyTessOCR(this,lenguaje);

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    ImageView vista = (ImageView)findViewById(R.id.vista);
        vista.setImageBitmap(bm);
        //doOCR(bm);
        //TextView txtreconocido=(TextView)findViewById(R.id.txtreconocido);
        //txtreconocido.setText(temp);
        //Reconocimeinto(bm);
    }

    //Reconociento de Texto
    private  void Reconocimeinto (Bitmap bitmap){
        // Toast.makeText(CapturarDatos.this,"aqui estoy",Toast.LENGTH_LONG).show();
        // process.setVisibility(View.VISIBLE);
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if(!textRecognizer.isOperational())
        {
            Toast.makeText(CapturarDatos.this,"No hay Textto",Toast.LENGTH_LONG).show();
        }
        else
        {

            Frame frame= new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> items = textRecognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();
            for(int i=0;i<items.size();i++)
            {
                TextBlock item=items.valueAt(i);
                stringBuilder.append(item.getValue());
                stringBuilder.append("\n");
                // Toast.makeText(CapturarDatos.this,stringBuilder,Toast.LENGTH_SHORT).show();

            }
            TextView txtreconocido=(TextView)findViewById(R.id.txtreconocido);
            txtreconocido.setText(stringBuilder.toString());
        }
        //process.setVisibility(View.INVISIBLE);

    }




    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mPath = savedInstanceState.getString("file_path");
    }


    private void SavePictures(String patch){
            SharedPreferences.Editor editor =sharedPreferences.edit();
            editor.putString("patch",patch);
            editor.apply();


    }


    private void doOCR (final Bitmap bitmap) {


        final String srcText = mTessOCR.getOCRResult(bitmap);

        /*
        ProgressDialog mProgressDialog=null;
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(ocrView, "Processing",
                    "Doing OCR...", true);
        } else {
            mProgressDialog.show();
        }
        new Thread(new Runnable() {
            public void run() {
                final String srcText = mTessOCR.getOCRResult(bitmap);
                ocrView.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if (srcText != null && !srcText.equals("")) {
                            //srcText contiene el texto reconocido
                        }
                        mTessOCR.onDestroy();
                        mProgressDialog.dismiss();
                    }
                });
            }
        }).start();*/
    }

    public void doOpenFlor(){


        String url="http://sicomsep.esy.es/Android/Usuarios/usuarios.php";

        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        params.put("Datos","DevolverUsuarios");
        client.post(url,params, new TextHttpResponseHandler() {

            ProgressDialog pd;

            public void onStart(){
                pd = new ProgressDialog(CapturarDatos.this);
                pd.setMessage("Cargando....");
                pd.setIndeterminate(false);
                pd.setCancelable(false);
                pd.show();
            }
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(contexto, "" + s, Toast.LENGTH_LONG).show();
                if (i == 404) {
                    Toast.makeText(contexto, "El archivo no existe", Toast.LENGTH_LONG).show();
                } else if (i == 500) {
                    Toast.makeText(contexto, "Error interno del servidor", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(contexto, "No se pudo conectar. Error de red.", Toast.LENGTH_SHORT).show();
                }
                pd.dismiss();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {


                    JSONObject object = new JSONObject(s);
                    JSONArray Jarray = object.getJSONArray("Usuarios");
                    String id,nombre,apellido,correo,telefono,localidad,perfil;

                    for(int c=0;c<Jarray.length();c++)
                    {
                        id = Jarray.getJSONObject(c).getString("Id");
                        nombre = Jarray.getJSONObject(c).getString("Nombre");
                        apellido = Jarray.getJSONObject(c).getString("Apellido");
                        correo = Jarray.getJSONObject(c).getString("Correo");
                        telefono = Jarray.getJSONObject(c).getString("Telefono");
                        localidad = Jarray.getJSONObject(c).getString("Localidad");
                        perfil = Jarray.getJSONObject(c).getString("Perfil");

                        //Toast.makeText(activity,apellido,Toast.LENGTH_LONG).show();
                        oDatos= new Usuarios(id,nombre,apellido,correo,telefono,localidad,perfil);
                        //llenarArrayDatos(oDatos);
                        //Toast.makeText(CapturarDatos.this,nombre,Toast.LENGTH_LONG).show();

                        //Toast.makeText(activity,nombre,Toast.LENGTH_LONG).show();
                    }

                    //iniciarListView();
                } catch (JSONException e) {
                    Toast.makeText(contexto, "Error al descargar datos. "+e.getMessage(), Toast.LENGTH_LONG).show();
                }
                pd.dismiss();
            }

            public void onFinish(){
                pd.dismiss();
            }
        });
    }

/*

    private ArrayList<Usuarios> llenarArrayDatos(Usuarios oFlor){
        array.add(oFlor);
        return array;
    }

    private void iniciarListView(){
        final String url ="http://sicomsep.esy.es/Android/Login/Usuarios/";

        oAdaptador=new Adaptador(activity, R.layout.vt_usuarios, array)
        {
            @Override
            public void onEntrada(Object obj, View v)
            {
                if (obj != null)
                {
                    TextView txtusuario=(TextView)v.findViewById(R.id.txtusuario);
                    TextView txtlocalidad=(TextView)v.findViewById(R.id.txtlocalidad);
                    TextView txtemail=(TextView)v.findViewById(R.id.txtemail);
                    CircleImageView imfperfil =(CircleImageView)v.findViewById(R.id.imgperfil);


                    txtusuario.setText(((Usuarios) obj).getNombre()+" "+((Usuarios) obj).getApellido());
                    txtlocalidad.setText(((Usuarios)obj).getLocalidad());
                    txtemail.setText(((Usuarios)obj).getCorreo());

                    String urlimagen=url+((Usuarios) obj).getPerfil();
                    Glide.with(activity)
                            .load(urlimagen)
                            .crossFade()

                            .thumbnail(0.5f)
                            .into(imfperfil);

                }
            }
        };
        listaDatos.setAdapter(oAdaptador);
        oAdaptador.notifyDataSetChanged();
        //listaDatos.setOnItemClickListener((AdapterView.OnItemClickListener) this);
    }*/
}
