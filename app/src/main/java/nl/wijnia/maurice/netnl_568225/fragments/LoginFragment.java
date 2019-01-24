package nl.wijnia.maurice.netnl_568225.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import nl.wijnia.maurice.netnl_568225.MainActivity;
import nl.wijnia.maurice.netnl_568225.R;
import nl.wijnia.maurice.netnl_568225.models.Repository;
import nl.wijnia.maurice.netnl_568225.models.User;

public class LoginFragment extends Fragment {

    Repository repository;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Set variables
        }
        repository = new Repository(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle(R.string.login);
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupLoginButton();
    }

    public void setupLoginButton() {
        final EditText txtUsername = getView().findViewById(R.id.login_username);
        final EditText txtPassword = getView().findViewById(R.id.login_password);
        Button btnLogin = getView().findViewById(R.id.login_button);
        final Repository repository = new Repository(getContext());
        final LoginFragment fragment = this;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.postLogin(fragment, txtUsername.getText().toString(), txtPassword.getText().toString());
            }
        });
    }

    public void onLoginSuccess(User user) {
        Toast.makeText(getContext(), String.format("Logged in as %s", user.username) , Toast.LENGTH_LONG).show();
        user.logIn();
        MainActivity activity = (MainActivity) getActivity();
        activity.checkCurrentUser();
        activity.navigateHome();
    }

    public void onLoginFail(String message) {
        Toast.makeText(getContext(), message , Toast.LENGTH_LONG).show();
    }
}
