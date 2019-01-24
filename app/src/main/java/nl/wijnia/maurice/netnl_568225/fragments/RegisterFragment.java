package nl.wijnia.maurice.netnl_568225.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import nl.wijnia.maurice.netnl_568225.R;
import nl.wijnia.maurice.netnl_568225.models.Repository;

public class RegisterFragment extends Fragment {
    Repository repository;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
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
        getActivity().setTitle(R.string.register);
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRegisterButton();
    }

    public void setupRegisterButton() {
        final EditText txtUsername = getView().findViewById(R.id.register_username);
        final EditText txtPassword = getView().findViewById(R.id.register_password);
        Button btnRegister = getView().findViewById(R.id.register_button);
        final Repository repository = new Repository(getContext());
        final RegisterFragment context = this;

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                repository.postRegister(context, username, password);
            }
        });
    }

    public void onRegisterSuccess() {
        Toast.makeText(getContext(), "Successfully registered, you can now log in!", Toast.LENGTH_LONG).show();
        Fragment fragment = LoginFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragment).commit();
    }

    public void onRegisterFail(String message) {
        Toast.makeText(getContext(), message , Toast.LENGTH_LONG).show();
    }
}
