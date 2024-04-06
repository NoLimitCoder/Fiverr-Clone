package com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

import com.example.csci3130_w24_group20_quick_cash.R;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

public class PaymentIntegrationFragment extends Fragment {

    private static final String TAG = PaymentIntegrationFragment.class.getName();

    private ActivityResultLauncher<Intent> activityResultLauncher;
    private PayPalConfiguration payPalConfig;

    private EditText enterAmtET;
    private Button payNowBtn;
    private TextView paymentStatusTV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_integration, container, false);
        init(view);
        configPayPal();
        initActivityLauncher();
        setListeners();
        return view;
    }

    private void init(View view) {
        enterAmtET = view.findViewById(R.id.enterAmtET);
        payNowBtn = view.findViewById(R.id.payNowBtn);
        paymentStatusTV = view.findViewById(R.id.paymentStatusTV);
    }

    private void configPayPal() {
        try {
            ApplicationInfo appInfo = requireActivity().getPackageManager().getApplicationInfo(requireActivity().getPackageName(), PackageManager.GET_META_DATA);
            Bundle metaData = appInfo.metaData;
            String clientId = metaData.getString("com.paypal.ClientId");
            if (clientId != null) {
                payPalConfig = new PayPalConfiguration()
                        .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                        .clientId(clientId);
            } else {
                Log.e(TAG, "PayPal client ID not found in manifest meta-data");
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Package name not found", e);
        }
    }

    private void initActivityLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        final PaymentConfirmation confirmation = result.getData().getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                        if (confirmation != null) {
                            try {
                                String paymentDetails = confirmation.toJSONObject().toString(4);
                                Log.i(TAG, paymentDetails);

                                JSONObject payObj = new JSONObject(paymentDetails);
                                String payID = payObj.getJSONObject("response").getString("id");
                                String state = payObj.getJSONObject("response").getString("state");
                                paymentStatusTV.setText(String.format("Payment %s%n with payment id is %s", state, payID));
                            } catch (JSONException e) {
                                Log.e("Error", "an extremely unlikely failure occurred: ", e);
                            }
                        }
                    } else if (result.getResultCode() == PaymentActivity.RESULT_EXTRAS_INVALID) {
                        Log.d(TAG, "Launcher Result Invalid");
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        Log.d(TAG, "Launcher Result Cancelled");
                    }
                });
    }

    private void setListeners() {
        payNowBtn.setOnClickListener(v -> processPayment());
    }

    private void processPayment() {
        final String amount = enterAmtET.getText().toString();
        final PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(
                amount), "CAD", "Purchase Goods", PayPalPayment.PAYMENT_INTENT_SALE);

        final Intent intent = new Intent(requireActivity(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        activityResultLauncher.launch(intent);
    }
}