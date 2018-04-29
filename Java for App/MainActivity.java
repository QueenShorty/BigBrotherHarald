import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 
import static android.view.View.GONE;
 
public class MainActivity extends AppCompatActivity 
{
 
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
 
    EditText editTextHeroId, editTextName, editTextRealname;
    //RatingBar ratingBar;
    //Spinner spinnerTeam;
    //ProgressBar progressBar;
    ListView listView;
    //Button buttonAddUpdate;
 
    /*List<Hero> heroList;
 
    boolean isUpdating = false;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        editTextHeroId = (EditText) findViewById(R.id.editTextHeroId);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextRealname = (EditText) findViewById(R.id.editTextRealname);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        spinnerTeam = (Spinner) findViewById(R.id.spinnerTeamAffiliation);
 
        buttonAddUpdate = (Button) findViewById(R.id.buttonAddUpdate);
 
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listViewHeroes);
 
        heroList = new ArrayList<>();
 
 
        buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUpdating) {
                    updateHero();
                } else {
                    createHero();
                }
            }
        });
        readHeroes();
    }*/ 
 
 
    private void createUser() 
            {
                String USERNAME = editTextUSERNAME.getText().toString().trim();
                String USERID = editTextUSERID.getText().toString().trim();
                String PHONETYPE = editTextPHONETYPE.getText().toString().trim(); 
             
                /*
                //validating the inputs
                if (TextUtils.isEmpty(name)) {
                    editTextName.setError("Please enter name");
                    editTextName.requestFocus();
                    return;
                }
         
                if (TextUtils.isEmpty(realname)) {
                    editTextRealname.setError("Please enter real name");
                    editTextRealname.requestFocus();
                    return;
                }*/ 
         
                //if validation passes 
                
                HashMap<String, String> params = new HashMap<>();
                params.put("USERNAME", USERNAME);
                params.put("USERID", USERID);
                params.put("PHONETYPE", PHONETYPE);
         
                
                //Calling the create USER API
                PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_USER, params, CODE_POST_REQUEST);
                request.execute();
            
            }
    private void readUser() 
            {
                PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_USER_LOC, null, CODE_GET_REQUEST);
                request.execute();
            }

    private void updateHero() //At this point
            {
                String USERNAME = editTextUSERNAME.getText().toString();
                String USERID = editTextUSERID.getText().toString().trim();
                String PHONETYPE = editTextPHONETYPE.getText().toString().trim();
                String USERLOCATION = editTextUSERLOCATION.getText().toString().trim(); 
         
                /*if (TextUtils.isEmpty(name)) 
                    {
                        editTextName.setError("Please enter name");
                        editTextName.requestFocus();
                        return;
                    }
         
                if (TextUtils.isEmpty(realname)) 
                    {
                        editTextRealname.setError("Please enter real name");
                        editTextRealname.requestFocus();
                        return;
                    }
                */ 
                HashMap<String, String> params = new HashMap<>();
                params.put("USERNAME", USERNAME);
                params.put("USERID", USERID);
                params.put("PHONETYPE", PHONETYPE);
                params.put("USERLOCATION", USERLOCATION);
         
         
                PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_USER, params, CODE_POST_REQUEST);
                request.execute();
                /*
                buttonAddUpdate.setText("Add");
         
                editTextName.setText("");
                editTextRealname.setText("");
                ratingBar.setRating(0);
                spinnerTeam.setSelection(0);
         
                isUpdating = false;*/ 
            }

            /*insert Delete here*/ 
    private void deleteHero(String USERID) 
            {
                PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DELETE_USER + USERID, null, CODE_GET_REQUEST);
                request.execute();
            }
 
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> 
    {
        String url;
        HashMap<String, String> params;
        int requestCode;
 
        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
 
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshHeroList(object.getJSONArray("heroes"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
 
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
 
            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);
 
 
            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);
 
            return null;
        }
    }
 
    /*  class HeroAdapter extends ArrayAdapter<Hero> 
    {
        List<Hero> heroList;
 
        public HeroAdapter(List<Hero> heroList) {
            super(MainActivity.this, R.layout.layout_hero_list, heroList);
            this.heroList = heroList;
        }
 
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) 
        {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_hero_list, null, true);
 
            TextView textViewName = listViewItem.findViewById(R.id.textViewName);
 
            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);
 
            final Hero hero = heroList.get(position);
 
            textViewName.setText(hero.getName());
 
            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isUpdating = true;
                    editTextHeroId.setText(String.valueOf(hero.getId()));
                    editTextName.setText(hero.getName());
                    editTextRealname.setText(hero.getRealname());
                    ratingBar.setRating(hero.getRating());
                    spinnerTeam.setSelection(((ArrayAdapter<String>) spinnerTeam.getAdapter()).getPosition(hero.getTeamaffiliation()));
                    buttonAddUpdate.setText("Update");
                }
            });
 
            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
 
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
 
                    builder.setTitle("Delete " + hero.getName())
                            .setMessage("Are you sure you want to delete it?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteHero(hero.getId());
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
 
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
 
                }
            });
 
            return listViewItem;
        }
    }*/ 
}