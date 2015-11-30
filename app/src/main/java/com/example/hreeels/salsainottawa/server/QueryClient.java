package com.example.hreeels.salsainottawa.server;

import com.example.hreeels.salsainottawa.core.Event;

import java.util.ArrayList;

/**
 * Created by Hreeels on 2015-11-27.
 *
 * This interface is to be implemented by any class which requires to talk
 * to the database.
 */
public interface QueryClient {

    /**
     * This function is called from the database class once
     * the query is complete. The class implementing this interface
     * can use this function to acquire the query result through the parameter.
     *
     * @param aQueryResult
     */
    public void queryDone(ArrayList<Event> aQueryResult);

}
