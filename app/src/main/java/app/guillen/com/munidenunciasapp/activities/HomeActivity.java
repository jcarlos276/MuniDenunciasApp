package app.guillen.com.munidenunciasapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import app.guillen.com.munidenunciasapp.R;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private String usuario;
    private TextView txtUsername;
    private TextView txtName;
    private TextView txtEmail;
    // SharedPreferences
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // init SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);

        // Setear Toolbar como action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set DrawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Set drawer toggle icon
        //final ActionBar ab = getSupportActionBar();
        //if (ab != null) {
        //    ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //    ab.setDisplayHomeAsUpEnabled(true);
        //}

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, android.R.string.ok, android.R.string.cancel);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        usuario = sharedPreferences.getString("username", null);


        // Set NavigationItemSelectedListener
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // Do action by menu item id
                switch (menuItem.getItemId()){
                    case R.id.nav_profile:
                        Toast.makeText(HomeActivity.this, "Perfil...", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_denuncias:
                        Intent denuncias = new Intent(HomeActivity.this,ListDenunciasActivity.class);
                        startActivity(denuncias);
                        break;
                    case R.id.nav_registrar_denuncia:
                        Intent registro = new Intent(HomeActivity.this,RegisterDenunciaActivity.class);
                        startActivity(registro);
                        break;
                    case R.id.nav_logout:
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        boolean success = editor
                                .putString("username", null)
                                .putBoolean("islogged", false)
                                .commit();
                        Intent main = new Intent(HomeActivity.this,MainActivity.class);
                        startActivity(main);
                        finish();
                }

                // Close drawer
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });

        // Change navigation header information
        ImageView photoImage = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.menu_photo);
        photoImage.setBackgroundResource(R.drawable.ic_profile);

        TextView fullnameText = (TextView) navigationView.getHeaderView(0).findViewById(R.id.menu_fullname);
        fullnameText.setText(usuario);
        //Toast.makeText(HomeActivity.this,ulogeado.getNombre().toString(),Toast.LENGTH_SHORT).show();

        txtUsername.setText(usuario);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // Option open drawer
                if(!drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.openDrawer(GravityCompat.START);   // Open drawer
                else
                    drawerLayout.closeDrawer(GravityCompat.START);    // Close drawer
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cerrarSesion(){
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
