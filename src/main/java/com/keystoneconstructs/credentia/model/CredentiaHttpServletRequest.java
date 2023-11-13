package com.keystoneconstructs.credentia.model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.*;

public class CredentiaHttpServletRequest extends HttpServletRequestWrapper {


    private final Map<String, String> customHeaders;


    public CredentiaHttpServletRequest( HttpServletRequest request ) {

        super( request );
        this.customHeaders = new HashMap<String, String>();
    }


    public void putHeader( String name, String value ) {

        this.customHeaders.put( name, value );
    }


    @Override
    public String getHeader( String name ) {

        String headerValue = customHeaders.get( name );

        if( headerValue != null ) {
            return headerValue;
        }

        return ( (HttpServletRequest) getRequest() ).getHeader( name );
    }


    @Override
    public Enumeration<String> getHeaderNames() {

        // create a set of the custom header names
        Set<String> set = new HashSet<>( customHeaders.keySet() );

        // now add the headers from the wrapped request object
        @SuppressWarnings( "unchecked" ) Enumeration<String> e = ( (HttpServletRequest) getRequest() ).getHeaderNames();
        while( e.hasMoreElements() ) {
            // add the names of the request headers into the list
            String n = e.nextElement();
            set.add( n );
        }

        // create an enumeration from the set and return
        return Collections.enumeration( set );
    }


    @Override
    public Enumeration<String> getHeaders( String name ) {

        List<String> values = Collections.list( super.getHeaders( name ) );
        if( customHeaders.containsKey( name ) ) {
            values.add( customHeaders.get( name ) );
        }
        return Collections.enumeration( values );
    }


}
