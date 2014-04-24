package com.cschryer.eaterofjsons;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.cschryer.eaterofjsons.classes.SpecialOffer;
import com.cschryer.eaterofjsons.dummy.DummyContent;

/**
 * A list fragment representing a list of Special Offers. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link SpecialOfferDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class SpecialOfferListFragment extends ListFragment {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SpecialOfferListFragment() {
    }
    
    public ArrayList<SpecialOffer> offersList = new ArrayList<SpecialOffer>();
    public Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new LoadOffersTask().execute();
        // TODO: replace with a real list adapter.  *Do this in the ASyncTask*
       /* setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                DummyContent.ITEMS));*/
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        context = activity.getApplicationContext();
        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(DummyContent.ITEMS.get(position).id);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
    
    
    //Start LoadOffersTask
    private class LoadOffersTask extends AsyncTask<Void, Void, Void>
	{
		
		protected void onPreExecute()
		{
			//TODO: show 'Loading' message (Toast or ProgressDialog) if unit testing calls for it
		}

		@Override
		protected Void doInBackground(Void... arg0)
		{
			android.os.Debug.waitForDebugger();
			try {
				String json_Url = getString(R.string.json_source_url);
				

				HttpClient hc = new DefaultHttpClient();
				HttpGet get = new HttpGet(json_Url);
				HttpResponse rp = hc.execute(get);

				JSONObject jsonObject = null;
				JSONObject root = null;
				JSONObject data = null;
				JSONArray offers = null;

				if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String result = EntityUtils.toString(rp.getEntity());
					result = "{\"offers\":" + result + "}";

					jsonObject = new JSONObject(result);
					//root = jsonObject.getJSONObject("");
					//data = jsonObject.getJSONObject("");
					offers = jsonObject.getJSONArray("offers");

					for (int i = 0; i < offers.length(); i++) {
						SpecialOffer offer = new SpecialOffer(offers.getJSONObject(i));
						//TODO: send JSON values to their... FINAL DESTINATION Dun dun dunnnnnn
						offersList.add(offer);
						
					}

				}
			}
			catch (Exception e) {
				Log.e("SpecialOfferListFragment", "Error loading JSON for Special Offer list", e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			//TODO: set list adapter
			setListAdapter(new OfferListAdapter(context, R.layout.activity_specialoffer_list, offersList));
		}
	}

	private class OfferListAdapter extends ArrayAdapter<SpecialOffer>
	{
		private ArrayList<SpecialOffer> listOfOffers;

		public OfferListAdapter(Context context, int textViewResourceId, ArrayList<SpecialOffer> items)
		{
			super(context, textViewResourceId, items);
			this.listOfOffers = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View v = convertView;
			if (v == null && context != null) {
				LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.activity_specialoffer_list, null);
				// v.setBackgroundColor(Color.TRANSPARENT);
			}
			SpecialOffer so = listOfOffers.get(position);
			ImageView iv = (ImageView) v.findViewById(R.id.offerDetailImg);
			//TODO: lazy load image in another thread 
			TextView tt = (TextView) v.findViewById(R.id.specialoffer_sponsor);
			tt.setText(so.getStrOfferSponsor());
			TextView tx = (TextView) v.findViewById(R.id.specialoffer_detail);
			tx.setText(so.getStrOfferDescription());
			
//			tt.setMovementMethod(LinkMovementMethod.getInstance());

			return v;

		}
		
		
	}
}


