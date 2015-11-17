package opengarden.yee.interview.yistest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import service.SolvePuzzleCallBack;
import service.SolvePuzzleService;

/**
 * Created by Yee on 11/11/15.
 */
public class PopulationPuzzleActivity extends Activity implements SolvePuzzleCallBack {

    TextView mResult;
    TextView mQuestion;
    Button mSubmit;
    ProgressDialog mDialog;
    SolvePuzzleService mService;

    public static final int TARGET = 100000000;
    private static final int[] defaultDataSet = {
            18897109, 12828837, 9461105, 6371773, 5965343, 5946800, 5582170, 5564635, 5268860, 4552402, 4335391,
            4296250, 4224851, 4192887, 3439809, 3279833, 3095313, 2812896, 2783243, 2710489, 2543482, 2356285,
            2226009, 2149127, 2142508, 2134411};
    String result = "No";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        mService = new SolvePuzzleService(this);
        mQuestion = (TextView) findViewById(R.id.input);
        mSubmit = (Button) findViewById(R.id.solveButton);
        mResult = (TextView) findViewById(R.id.result);
//        mResult.setText(result);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService();
            }
        });
    }

    @Override
    public void onActionSuccess() {
        mDialog.hide();
        mResult.setText(mService.getResult().toString());
    }

    @Override
    public void onActionFailed() {
        mDialog.hide();
        mResult.setText(getString(R.string.no_solution));
    }

    private void startService() {
        mDialog = new ProgressDialog(this);
        mDialog.setMessage(getString(R.string.loading));
        mDialog.setCancelable(false);
        mDialog.show();
        mService.solvePuzzle(getArrayList(defaultDataSet), TARGET, new ArrayList<Integer>());
    }

    private ArrayList<Integer> getArrayList(int[] array) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i : array) result.add(i);
        return result;
    }
}
