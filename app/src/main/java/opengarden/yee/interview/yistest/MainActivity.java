package opengarden.yee.interview.yistest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by Yee on 11/11/15.
 */
public class MainActivity extends Activity {

    TextView mResult;
    private static final int[] dataSet = {
            18897109, 12828837, 9461105, 6371773, 5965343, 5946800, 5582170, 5564635, 5268860, 4552402, 4335391,
            4296250, 4224851, 4192887, 3439809, 3279833, 3095313, 2812896, 2783243, 2710489, 2543482, 2356285,
            2226009, 2149127, 2142508, 2134411
    };
    private static final int[] testSet = {
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        PopulationSubset populationSubset = new PopulationSubset(testSet);
        mResult = (TextView) findViewById(R.id.result);
        mResult.setText(Arrays.toString(populationSubset.getDataSet()));
    }
}
