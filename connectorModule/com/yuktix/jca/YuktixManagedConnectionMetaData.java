/*
 * The MIT License
 *
 * Copyright 2014 Sven Varkel <sven@mageflow.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.yuktix.jca;

import javax.resource.ResourceException;
import javax.resource.spi.ManagedConnectionMetaData;

/**
 *
 * @author Sven Varkel <sven@mageflow.com>
 */
public class YuktixManagedConnectionMetaData implements ManagedConnectionMetaData {

    /**
     *
     */
    public YuktixManagedConnectionMetaData() {
    }

    /**
     *
     * @return @throws ResourceException
     */
    @Override
    public String getEISProductName() throws ResourceException {
        return "BeanstalkdConnector";
    }

    /**
     *
     * @return @throws ResourceException
     */
    @Override
    public String getEISProductVersion() throws ResourceException {
        return "1.0";
    }

    /**
     *
     * @return @throws ResourceException
     */
    @Override
    public int getMaxConnections() throws ResourceException {
        return 1;
    }

    /**
     *
     * @return @throws ResourceException
     */
    @Override
    public String getUserName() throws ResourceException {
        return null;
    }

}