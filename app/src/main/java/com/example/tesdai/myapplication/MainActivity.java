package com.example.tesdai.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tesdai.myapplication.databinding.ActivityMainBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    private static final int[] NUMBERS = {10, 7, 8, 2, 4, 1};
    private static final long DEMO_DELAY_MILLIS = 1_000L;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private ActivityMainBinding binding;
    private Future<?> currentTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.progressBar.setMax(NUMBERS.length);
        binding.startButton.setOnClickListener(view -> startSum());
        binding.cancelButton.setOnClickListener(view -> cancelSum());
    }

    private void startSum() {
        if (binding == null || currentTask != null) {
            return;
        }

        setRunningState(true);
        binding.progressBar.setProgress(0);
        binding.statusText.setText(getString(R.string.working, 0, NUMBERS.length));

        currentTask = executor.submit(() -> {
            try {
                long total = ProgressiveSummer.sum(
                        NUMBERS,
                        DEMO_DELAY_MILLIS,
                        (completed, count) -> mainHandler.post(
                                () -> showProgress(completed, count)
                        )
                );
                mainHandler.post(() -> showResult(total));
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                mainHandler.post(this::showCancelled);
            } catch (ArithmeticException exception) {
                mainHandler.post(this::showFailure);
            }
        });
    }

    private void cancelSum() {
        Future<?> task = currentTask;
        if (task != null) {
            task.cancel(true);
        }
    }

    private void showProgress(int completed, int total) {
        if (!canUpdateUi() || currentTask == null) {
            return;
        }
        binding.progressBar.setProgress(completed);
        binding.statusText.setText(getString(R.string.working, completed, total));
    }

    private void showResult(long result) {
        if (!canUpdateUi()) {
            return;
        }
        binding.progressBar.setProgress(NUMBERS.length);
        binding.statusText.setText(getString(R.string.finished, result));
        currentTask = null;
        setRunningState(false);
    }

    private void showCancelled() {
        if (!canUpdateUi()) {
            return;
        }
        binding.statusText.setText(R.string.cancelled);
        currentTask = null;
        setRunningState(false);
    }

    private void showFailure() {
        if (!canUpdateUi()) {
            return;
        }
        binding.statusText.setText(R.string.failed);
        currentTask = null;
        setRunningState(false);
    }

    private void setRunningState(boolean running) {
        if (binding == null) {
            return;
        }
        binding.startButton.setEnabled(!running);
        binding.cancelButton.setEnabled(running);
    }

    private boolean canUpdateUi() {
        return binding != null && !isFinishing() && !isDestroyed();
    }

    @Override
    protected void onDestroy() {
        if (currentTask != null) {
            currentTask.cancel(true);
            currentTask = null;
        }
        executor.shutdownNow();
        mainHandler.removeCallbacksAndMessages(null);
        binding = null;
        super.onDestroy();
    }
}
