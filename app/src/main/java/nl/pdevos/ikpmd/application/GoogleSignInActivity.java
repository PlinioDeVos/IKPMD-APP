package nl.pdevos.ikpmd.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import nl.pdevos.ikpmd.R;
import nl.pdevos.ikpmd.database.User;

public class GoogleSignInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private User user;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_in);

        mAuth = FirebaseAuth.getInstance();

        setupUser();
        configureOptions();
    }

    private void setupUser() {
        user = User.getInstance();
        user.setUser(mAuth.getCurrentUser());
    }

    private void configureOptions() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent intent = result.getData();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);

            try {
                // Inloggen met Google gelukt, authenticeren met Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Inloggen met Google mislukt, laat toast bericht zien
                Toast.makeText(GoogleSignInActivity.this, "Inloggen mislukt!", Toast.LENGTH_SHORT).show();
            }
        }
    });

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Als het inloggen lukt, laat toast bericht zien en breng de gebruiker terug
                        Toast.makeText(GoogleSignInActivity.this, "Inloggen gelukt", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                    } else {
                        // Als het inloggen mislukt, laat toast bericht zien
                        Toast.makeText(GoogleSignInActivity.this, "Inloggen mislukt!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Wanneer de gebruiker al bestaat (dus de gebruiker is ingelogd), breng de gebruiker naar de Main.
        if (user.getUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void signinClicked(View v) {
        resultLauncher.launch(new Intent(mGoogleSignInClient.getSignInIntent()));
    }
}