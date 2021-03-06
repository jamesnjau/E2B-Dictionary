package com.rama.dictionary.activity;

import java.util.ArrayList;
import java.util.Locale;

import com.rama.dictionary.Bean;
import com.rama.dictionary.BookMarksAdapter;
import com.rama.dictionary.BookMarksDBHelper;
import com.rama.dictionary.R;
import com.rama.dictionary.R.id;
import com.rama.dictionary.R.layout;
import com.rama.dictionary.R.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class BookMarksActivity extends Activity {
	ArrayList<Bean> bookMarksWordList;
	BookMarksAdapter bookMarksAdapter;
	BookMarksDBHelper bookMarksDBHelper;
	EditText etSearchWord;
	ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_marks_acivity);
		bookMarksDBHelper = BookMarksDBHelper
				.getDbHelperInstance(getApplicationContext());
		lv = (ListView) findViewById(R.id.bookmarkListView);
		etSearchWord = (EditText) findViewById(R.id.etBookMarksSearch);
		// load bookmarks

		loadBookmarks();

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View v,
					int position, long id) {
				final String word_eng = bookMarksWordList.get(position)
						.getEngWord();

				AlertDialog.Builder builder = new AlertDialog.Builder(
						BookMarksActivity.this);

				builder.setTitle("remove bookmarks");
				builder.setMessage("do you delete bookmarks...");

				builder.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								bookMarksDBHelper.removeBookMarks(word_eng);
								loadBookmarks();
							}
						});

				builder.setNegativeButton("No",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();

							}
						});

				AlertDialog alertDialog = builder.create();

				alertDialog.show();
				return false;
			}
		});

	}

	public void loadBookmarks() {
		bookMarksWordList = bookMarksDBHelper.getAllBooksMarks();
		bookMarksAdapter = new BookMarksAdapter(getApplicationContext(),
				bookMarksWordList);
		lv.setAdapter(bookMarksAdapter);
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		etSearchWord.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				String text = etSearchWord.getText().toString()
						.toLowerCase(Locale.getDefault());
				bookMarksAdapter.filter(text);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.book_marks, menu);
		return true;
	}

}
