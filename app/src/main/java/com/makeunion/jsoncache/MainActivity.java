package com.makeunion.jsoncache;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

import android.Manifest;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.makeunion.jsoncachelib.api.JsonCache;
import com.makeunion.jsoncachelib.callback.ICallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_clear_all) {
            JsonCache.getInstance().clearAll();
        }
        return true;
    }

    // ========================== Object ==========================

    public void saveObject(View view) {
        User user = new User("张三", 12);
        JsonCache.getInstance().saveObject("user", user);
    }

    public void loadObject(View view) {
        User user = JsonCache.getInstance().loadObject("user", User.class);
        Toast.makeText(this, user == null ? "null" : user.toString(), Toast.LENGTH_LONG).show();
    }

    public void saveObjectAsync(View view) {
        User user = new User("张三", 12);
        JsonCache.getInstance().saveObjectAsync("user", user);
    }

    public void loadObjectAsync(View view) {
        JsonCache.getInstance().loadObjectAsync("user", User.class, new ICallback<User>() {
            @Override
            public void onResult(User user) {
                Toast.makeText(MainActivity.this, user == null ? "null" : user.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // ========================== String ==========================

    public void saveString(View view) {
        JsonCache.getInstance().saveString("stringValue", "JsonCache");
    }

    public void loadString(View view) {
        String cache = JsonCache.getInstance().loadString("stringValue");
        Toast.makeText(this, cache == null ? "null" : cache, Toast.LENGTH_LONG).show();
    }

    public void saveStringAsync(View view) {
        JsonCache.getInstance().saveStringAsync("stringValue", "JsonCache");
    }

    public void loadStringAsync(View view) {
        JsonCache.getInstance().loadStringAsync("stringValue", new ICallback<String>() {
            @Override
            public void onResult(String result) {
                Toast.makeText(MainActivity.this, result == null ? "null" : result, Toast.LENGTH_LONG).show();
            }
        });
    }

    // ========================== Integer ==========================

    public void saveInteger(View view) {
        JsonCache.getInstance().saveInt("intValue", 12);
    }

    public void loadInteger(View view) {
        int cache = JsonCache.getInstance().loadInt("intValue", 0);
        Toast.makeText(this, String.valueOf(cache), Toast.LENGTH_LONG).show();
    }

    public void saveIntegerAsync(View view) {
        JsonCache.getInstance().saveIntAsync("intValue", 12);
    }

    public void loadIntegerAsync(View view) {
        JsonCache.getInstance().loadIntAsync("intValue", 0, new ICallback<Integer>() {
            @Override
            public void onResult(Integer result) {
                Toast.makeText(MainActivity.this, String.valueOf(result), Toast.LENGTH_LONG).show();
            }
        });
    }

    // ========================== Float ==========================

    public void saveFloat(View view) {
        JsonCache.getInstance().saveFloat("floatValue", 88.15f);
    }

    public void loadFloat(View view) {
        float cache = JsonCache.getInstance().loadFloat("floatValue", 0);
        Toast.makeText(this, String.valueOf(cache), Toast.LENGTH_LONG).show();
    }

    public void saveFloatAsync(View view) {
        JsonCache.getInstance().saveFloatAsync("floatValue", 88.15f);
    }

    public void loadFloatAsync(View view) {
        JsonCache.getInstance().loadFloatAsync("floatValue", 0, new ICallback<Float>() {
            @Override
            public void onResult(Float result) {
                Toast.makeText(MainActivity.this, String.valueOf(result), Toast.LENGTH_LONG).show();
            }
        });
    }

    // ========================== Double ==========================

    public void saveDouble(View view) {
        JsonCache.getInstance().saveDouble("doubleValue", 3.14d);
    }

    public void loadDouble(View view) {
        double cache = JsonCache.getInstance().loadDouble("doubleValue", 0);
        Toast.makeText(this, String.valueOf(cache), Toast.LENGTH_LONG).show();
    }

    public void saveDoubleAsync(View view) {
        JsonCache.getInstance().saveDoubleAsync("doubleValue", 3.14d);
    }

    public void loadDoubleAsync(View view) {
        JsonCache.getInstance().loadDoubleAsync("doubleValue", 0, new ICallback<Double>() {
            @Override
            public void onResult(Double result) {
                Toast.makeText(MainActivity.this, String.valueOf(result), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int result : grantResults) {
            if (result != PERMISSION_GRANTED) {
                finish();
            }
        }
    }
}
