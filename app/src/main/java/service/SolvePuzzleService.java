package service;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by Yee on 11/16/15.
 */
public class SolvePuzzleService {

    ArrayList<ArrayList<Integer>> mResult;
    SolvePuzzleCallBack mMainThreadCallBack;
    boolean mFlag = false;

    public ArrayList<ArrayList<Integer>> getResult() {
        return mResult;
    }

    public SolvePuzzleService(SolvePuzzleCallBack mainCallBack) {
        mMainThreadCallBack = mainCallBack;
    }

    public void solvePuzzle(final ArrayList<Integer> sampleArray, final int target, final ArrayList<Integer> subArray) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                mResult = new ArrayList<ArrayList<Integer>>();
                processing(sampleArray, target, subArray);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(mFlag)mMainThreadCallBack.onActionSuccess();
                else mMainThreadCallBack.onActionFailed();
            }
        }.execute();
    }

    void processing(ArrayList<Integer> sampleArray, int target, ArrayList<Integer> subArray) {
        int sum = 0;
        for (int i : subArray) sum += i;
        if (sum == target) {
            mResult.add(subArray);
            mFlag = true;
        }
        if (sum > target) return;
        for (int i = 0; i < sampleArray.size(); i++) {
            ArrayList<Integer> remainArray = new ArrayList<>();
            int n = sampleArray.get(i);
            for (int j = i + 1; j < sampleArray.size(); j++) remainArray.add(sampleArray.get(j));
            ArrayList<Integer> newSubArray = new ArrayList<>(subArray);
            newSubArray.add(n);
            processing(remainArray, target, newSubArray);
        }
    }
}
