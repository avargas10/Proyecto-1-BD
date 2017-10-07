package Interfaz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adrian.gspapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Adrian on 21/01/2016.
 */
public class PostAdapter extends BaseAdapter {
    // Declare Variables
    Context context;
    JSONArray listaPost;
    LayoutInflater inflater;
    JSONObject obj;

    //Funcion en la que se pasa como parametro el JsonArray creado con los obetos json, para poder insertarlos en el post
    public PostAdapter(Context context, JSONArray listaPost) {
        this.context = context;
        this.listaPost=listaPost;
    }

    @Override
    public int getCount() {
        return listaPost.length();
    }

    public void delete(int pos){
        try {
            Toast.makeText(context, "Se ha eliminado el archivo "+ this.listaPost.getJSONObject(pos).getString("titulo")+"."+
                            this.listaPost.getJSONObject(pos).getString("contenido"),
                    Toast.LENGTH_LONG).show();
            this.listaPost.remove(pos);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getItem(int position) {
        try {
            return listaPost.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txtTitulo;
        TextView txtContenido;
        ImageView ImgEstado;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.post_principal, parent, false);

        // Locate the TextViews in listview_item.xml
        txtTitulo = (TextView) itemView.findViewById(R.id.titulo_postPrincipal);
        txtContenido = (TextView) itemView.findViewById(R.id.descripcion_postPrincipal);
        ImgEstado=(ImageView) itemView.findViewById(R.id.imagen1);

        // Capture position and set to the TextViews
        try {
            /****Se toma el objeto en la posicion de la variable "position", del jsonArray, se toma el titulo, contenido e indicador
            que son quienes componen el objeto json extraido del jsonArray, y se insertan en el post****/
            obj=listaPost.getJSONObject(position);
            txtTitulo.setText(obj.getString("titulo").toString());
            txtContenido.setText(obj.getString("contenido").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return itemView;
    }

}
