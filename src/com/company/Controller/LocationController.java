package com.company.Controller;

import com.company.DbHelper.LocationRepository;
import com.company.Models.Customer;
import com.company.Models.Location;

public class LocationController {

    public static Location getLocation(Customer customer) {
        LocationRepository locationRepository = new LocationRepository();

        return locationRepository.getLocation(customer);
    }
}
