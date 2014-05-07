package br.com.tonismar.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by tonismar on 5/5/14.
 */
public class OcorrenciaAdapter extends BaseAdapter {

    private final Context ctx;
    private final List<Ocorrencia> ocorrencias;

    public OcorrenciaAdapter(Context ctx, List<Ocorrencia> ocorrencias) {
        this.ctx = ctx;
        this.ocorrencias = ocorrencias;
    }

    @Override
    public int getCount() {
        return ocorrencias.size();
    }

    @Override
    public Object getItem(int i) {
        return ocorrencias.get(i);
    }

    @Override
    public long getItemId(int i) {
        return ocorrencias.get(i).getId();
    }

    @Override
    public View getView(int pos, View viewAtual, ViewGroup arg2) {

        ViewHolder holder = null;
        if (viewAtual == null){
            LayoutInflater inflater = (LayoutInflater)
                    ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.list_item, null);
            TextView txtUser = (TextView) v.findViewById(R.id.txtUser);
            TextView txtMensagem = (TextView) v.findViewById(R.id.txtMensagem);
            ImageView imgImagem = (ImageView) v.findViewById(R.id.imgImagem);

            holder = new ViewHolder();
            holder.txtUser = txtUser;
            holder.txtMensagem = txtMensagem;
            holder.imgImagem = imgImagem;

            v.setTag(holder);
            viewAtual = v;
        } else {
            holder = (ViewHolder) viewAtual.getTag();
        }



        Ocorrencia ocorrencia = ocorrencias.get(pos);
        holder.txtUser.setText(ocorrencia.getUser());
        holder.txtMensagem.setText(ocorrencia.getMensagem());
        holder.imgImagem.setImageBitmap(getBitmapFromURL(ocorrencia.getImagem()));
        holder.ocorrencia = ocorrencia;

        return viewAtual;
    }

    /* Tudo isso só para carregar a imagem da url */
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL("http://spykids.bl.ee/img/" + src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    class ViewHolder {
        public TextView txtUser;
        public TextView txtMensagem;
        public ImageView imgImagem;
        public Ocorrencia ocorrencia = null;
    }
}
