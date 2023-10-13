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
package co.openworkflow.spin.json;

import co.openworkflow.spin.DeserializationTypeValidator;
import co.openworkflow.spin.spi.DataFormatMapper;
import org.junit.Before;
import org.mockito.Mockito;

public class JsonSerializationWithValidationTest extends JsonSerializationTest {

  protected DeserializationTypeValidator validator;

  @Before
  public void setUpValidator() {
    validator = Mockito.mock(DeserializationTypeValidator.class);
    Mockito.when(validator.validate(Mockito.anyString())).thenReturn(true);
  }

  @Override
  protected Object doDeserialization(DataFormatMapper mapper, Object mappedObject, String objectTypeName) {
    return mapper.mapInternalToJava(mappedObject, objectTypeName, validator);
  }

}
