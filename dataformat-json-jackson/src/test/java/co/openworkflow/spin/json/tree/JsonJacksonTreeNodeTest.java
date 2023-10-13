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
package co.openworkflow.spin.json.tree;

import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;
import static co.openworkflow.spin.Spin.S;
import static co.openworkflow.spin.json.JsonTestConstants.EXAMPLE_JSON;

import java.io.StringWriter;

import co.openworkflow.spin.impl.json.jackson.JacksonJsonNode;
import org.junit.Before;
import org.junit.Test;

public class JsonJacksonTreeNodeTest {

  protected JacksonJsonNode jsonNode;

  @Before
  public void parseJson() {
    jsonNode = S(EXAMPLE_JSON);
  }

  @Test
  public void canWriteToString() {
    assertThatJson(jsonNode.toString()).isEqualTo(EXAMPLE_JSON);
  }

  @Test
  public void canWriteToWriter() {
    StringWriter writer = new StringWriter();
    jsonNode.writeToWriter(writer);
    String value = writer.toString();
    assertThatJson(value).isEqualTo(EXAMPLE_JSON);
  }

}
