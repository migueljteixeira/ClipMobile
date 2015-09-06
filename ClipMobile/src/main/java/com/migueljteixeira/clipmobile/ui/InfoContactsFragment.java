package com.migueljteixeira.clipmobile.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.migueljteixeira.clipmobile.R;
import com.migueljteixeira.clipmobile.adapters.InfoContactsListViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InfoContactsFragment extends BaseFragment {

    @Bind(R.id.list_view) ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view, container, false);
        ButterKnife.bind(this, view);

        InfoContactsListViewAdapter adapter = new InfoContactsListViewAdapter(getActivity());
        Resources resources = getResources();

        // Set 'internal contacts' title
        adapter.add(new ContactTitle(resources.getString(R.string.info_contacts_internal_title)));

        // Add 'internal contacts'
        String[] contacts = resources.getStringArray(R.array.info_contacts_internal);
        for(int i=0; i<contacts.length; i+=3)
            adapter.add(new ContactInternal(contacts[i], contacts[i+1], contacts[i+2]));

        // Set 'external contacts' title
        adapter.add(new ContactTitle(resources.getString(R.string.info_contacts_external_title)));

        // Add 'external contacts'
        contacts = resources.getStringArray(R.array.info_contacts_external);
        for(int i=0; i<contacts.length; i+=2)
            adapter.add(new ContactExternal(contacts[i], contacts[i+1]));

        mListView.setAdapter(adapter);
        return view;
    }

    public static class ContactTitle {
        public String name;

        public ContactTitle(String name) {
            this.name = name;
        }
    }

    public static class ContactExternal extends ContactTitle {
        public String phone;

        public ContactExternal(String name, String phone) {
            super(name);
            this.phone = phone;
        }
    }

    public static class ContactInternal extends ContactExternal {
        public String schedule;

        public ContactInternal(String name, String phone, String schedule) {
            super(name, phone);
            this.schedule = schedule;
        }
    }

}
