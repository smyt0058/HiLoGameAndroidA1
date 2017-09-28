
/**
 * Hi-Lo
 * A number guessing game
 *
 * @author Jason Smyth (smyt0058@algonquinlive.com)
 */

package com.algonquincollege.smyt0058.hi_logame;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

import static com.algonquincollege.smyt0058.hi_logame.MainActivity.LoginActivity.ABOUT_DIALOG_TAG;
import static com.algonquincollege.smyt0058.hi_logame.R.id.userGuessField;

public class MainActivity extends AppCompatActivity {

    //Initializing variables and instance of Random class
    int theNumber;
    int numGuesses;
    int userGuess;
    final int maxNumGuesses = 10;
    Random rand = new Random();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText guessField = (EditText) findViewById(userGuessField);



        //Generating theNumber
        theNumber = rand.nextInt(1000) + 1;

        Button guessBtn = (Button) findViewById(R.id.guess);
        guessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                //incrementing numGuesses on click
                numGuesses++;

                //User input validation
                if (guessField.getText().toString().matches("")) {
                    guessField.setError(getString(R.string.invalid_number));
                    Toast.makeText(MainActivity.this, getString(R.string.invalid_numberMsg), Toast.LENGTH_SHORT).show();
                    numGuesses--;//Invalid input does not count as a guess
                    return;
                }

                //parsing input string into an integer
                userGuess = Integer.parseInt(guessField.getText().toString());

                if (userGuess < 1 || userGuess > 1000) {
                    guessField.setText("");
                    guessField.setError(getString(R.string.invalid_number));
                    Toast.makeText(MainActivity.this, getString(R.string.invalid_numberMsg), Toast.LENGTH_SHORT).show();
                    numGuesses--;//Invalid input does not count as a guess
                    return;
                }


                //Guess checks & Win/Lose conditions
                if (userGuess < theNumber && numGuesses < maxNumGuesses) {
                    Toast.makeText(MainActivity.this, getString(R.string.too_low) + " You have " + (maxNumGuesses - numGuesses) + " Guesses left", Toast.LENGTH_SHORT).show();
                } else if (userGuess > theNumber && numGuesses < maxNumGuesses) {
                    Toast.makeText(MainActivity.this, getString(R.string.too_high) + " You have " + (maxNumGuesses - numGuesses) + " Guesses left", Toast.LENGTH_SHORT).show();
                } else if (userGuess == theNumber && numGuesses <= 5) {
                    Toast.makeText(MainActivity.this, getString(R.string.superior_win), Toast.LENGTH_SHORT).show();
                    numGuesses = 0;
                    theNumber = rand.nextInt(1000);
                    guessField.setText("");
                } else if (userGuess == theNumber && numGuesses <= maxNumGuesses) {
                    Toast.makeText(MainActivity.this, getString(R.string.excellent_win), Toast.LENGTH_SHORT).show();
                    numGuesses = 0;
                    theNumber = rand.nextInt(1000);
                    guessField.setText("");
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.please_reset), Toast.LENGTH_SHORT).show();
                }


            }
        });

        //Resets game
        Button resetBtn = (Button) findViewById(R.id.reset);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessField.setText("");
                Toast.makeText(MainActivity.this, "Game has been reset", Toast.LENGTH_SHORT).show();
                numGuesses = 0;
                theNumber = rand.nextInt(1000);
            }
        });

        //Resets game & shows theNumber
        resetBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                guessField.setText("");
                Toast.makeText(MainActivity.this, "The number was " + String.valueOf(theNumber) + ". Let's give it another go!", Toast.LENGTH_SHORT).show();
                numGuesses = 0;
                theNumber = rand.nextInt(1000);
                return true;
            }
        });

    }


    //Inflating and initializing Dialog
    public class LoginActivity extends Activity {
        // CLASS VARIABLES (by convention, class vars in upper-case)
        public static final String ABOUT_DIALOG_TAG = "About Dialog";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();
        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
