package br.com.tonismar.app;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ListActivity {

    private Context context;
    private static String url = "http://spykids.bl.ee/list.php?list=new";

    private static final String DIA = "dia";
    private static final String HORA = "hora";
    private static final String MENSAGEM = "mensagem";
    private static final String USER = "user";
    private static final String IMAGEM = "imagem";
    private static final String CHECKED = "checked";
    private static final String ID = "id";

    private List<Ocorrencia> ocorrencias;

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new ProgressTask(MainActivity.this).execute();
        //lv = (ListView) findViewById(R.id.list);
    }

    private class ProgressTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog dialog;

        private ListActivity activity;

        public ProgressTask(ListActivity activity) {
            this.activity = activity;
            context = activity;
            dialog = new ProgressDialog(context);
        }

        private Context context;

        protected void onPreExecute() {
            this.dialog.setMessage("Progress start");
            if (!activity.isFinishing()) {
                this.dialog.show();
            }
        }

        @Override
        protected  void onPostExecute(final Boolean success) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
/*
            ListAdapter adapter = new SimpleAdapter(context, jsonlist,
                    R.layout.list_item, new String[] { DIA, HORA,
                                    MENSAGEM }, new int[] {
                                    R.id.txtdia, R.id.txthora, R.id.txtmensagem });
            setListAdapter(adapter);
            lv = getListView();
*/
            OcorrenciaAdapter adapter = new OcorrenciaAdapter(context, ocorrencias);
            setListAdapter(adapter);
            lv = getListView();
        }

        protected Boolean doInBackground(final String... args) {

            JSONParser jsonParser = new JSONParser();
            JSONArray json = jsonParser.getJSONFromUrl(url);
            ocorrencias = new ArrayList<Ocorrencia>();

            for (int i = 0; i < json.length(); i++)
                try {
                    JSONObject c = json.getJSONObject(i);
                    Ocorrencia occ = new Ocorrencia();
                    occ.setChecked(Boolean.getBoolean(c.getString(CHECKED)));
                    occ.setDia(c.getString(DIA));
                    occ.setHora(c.getString(HORA));
                    occ.setImagem(c.getString(IMAGEM));
                    occ.setUser(c.getString(USER));
                    occ.setMensagem(c.getString(MENSAGEM));
                    occ.setId(Integer.parseInt(c.getString(ID)));
                    ocorrencias.add(occ);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            return null;
        }

        public void finish() {
            if(dialog.isShowing()){
                dialog.dismiss();
            }
            activity.finish();
        }
    }
}
