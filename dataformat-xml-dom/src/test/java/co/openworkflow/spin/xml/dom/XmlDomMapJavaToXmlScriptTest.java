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
package co.openworkflow.spin.xml.dom;

import static org.assertj.core.api.Assertions.assertThat;
import static co.openworkflow.spin.xml.XmlTestConstants.EXAMPLE_VALIDATION_XML;
import static co.openworkflow.spin.xml.XmlTestConstants.createExampleOrder;

import co.openworkflow.spin.impl.test.Script;
import co.openworkflow.spin.impl.test.ScriptTest;
import co.openworkflow.spin.xml.mapping.Order;
import org.junit.Test;

public abstract class XmlDomMapJavaToXmlScriptTest extends ScriptTest{

  @Test
  @Script(execute = false)
  public void shouldMapJavaToXml() throws Throwable {
    Order order = createExampleOrder();

    script.setVariable("input", order);
    script.execute();
    String xml = script.getVariable("xml");

    assertThat(xml).isXmlEqualTo(EXAMPLE_VALIDATION_XML);
  }

  @Test(expected = IllegalArgumentException.class)
  @Script(execute = false)
  public void shouldFailWithNull() throws Throwable {
    failingWithException();
  }
}
