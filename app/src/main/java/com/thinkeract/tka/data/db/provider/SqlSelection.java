package com.thinkeract.tka.data.db.provider;

import android.net.Uri;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * This class encapsulates a SQL where clause and its parameters. It makes it
 * possible for shared methods (like
 * {@link ContactProvider#getWhereClause(Uri, String, String[], int)}) to return
 * both pieces of information, and provides some utility logic to ease
 * piece-by-piece construction of selections.
 */
public class SqlSelection {
	public StringBuilder mWhereClause = new StringBuilder();
	public List<String> mParameters = new ArrayList<String>();

	public <T> void appendClause(String newClause, final T... parameters) {
		if (TextUtils.isEmpty(newClause)) {
			return;
		}
		if (mWhereClause.length() != 0) {
			mWhereClause.append(" AND ");
		}
		mWhereClause.append("(");
		mWhereClause.append(newClause);
		mWhereClause.append(")");
		if (parameters != null) {
			for (Object parameter : parameters) {
				if (parameter != null)
					mParameters.add(parameter.toString());
			}
		}
	}

	public String getSelection() {
		return mWhereClause.toString();
	}

	public String[] getParameters() {
		String[] array = new String[mParameters.size()];
		return mParameters.toArray(array);
	}
}