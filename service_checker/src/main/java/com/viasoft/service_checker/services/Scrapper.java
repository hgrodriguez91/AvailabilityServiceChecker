package com.viasoft.service_checker.services;

import java.io.IOException;

public interface Scrapper {

    void loadServiceStatus() throws IOException;
}
