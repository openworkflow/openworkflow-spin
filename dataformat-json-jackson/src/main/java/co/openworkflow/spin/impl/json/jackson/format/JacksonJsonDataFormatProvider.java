/*
 * Copyright Summit58 LLC and/or licensed to Summit58 LLC
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Summit58 licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package co.openworkflow.spin.impl.json.jackson.format;

import static co.openworkflow.spin.DataFormats.JSON_DATAFORMAT_NAME;

import java.util.Set;

import co.openworkflow.spin.spi.DataFormat;
import co.openworkflow.spin.spi.DataFormatProvider;

/**
 * Provides the {@link JacksonJsonDataFormat} with default configuration.
 *
 * @author Daniel Meyer
 *
 */
public class JacksonJsonDataFormatProvider implements DataFormatProvider {

  protected Set<String> names;

  public String getDataFormatName() {
    return JSON_DATAFORMAT_NAME;
  }

  public DataFormat<?> createInstance() {
    return new JacksonJsonDataFormat(JSON_DATAFORMAT_NAME);
  }
}
