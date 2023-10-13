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
import static org.assertj.core.api.Assertions.assertThat;
import static co.openworkflow.spin.json.JsonTestConstants.EXAMPLE_JSON;

import co.openworkflow.spin.SpinList;
import co.openworkflow.spin.impl.test.Script;
import co.openworkflow.spin.impl.test.ScriptTest;
import co.openworkflow.spin.json.SpinJsonNode;
import org.junit.Test;

public abstract class JsonTreeMapObjectToJsonScriptTest extends ScriptTest {

  @Test
  @Script
  public void shouldMapDictionary() {

    String json = script.getVariable("json");
    assertThat(json).isNotNull();
    assertThatJson(json).isEqualTo(EXAMPLE_JSON);
  }

  @Test
  @Script
  public void shouldMapPrimitives() {
    SpinJsonNode json = script.getVariable("stringVar");

    assertThat(json.isString()).isTrue();
    assertThat(json.stringValue()).isEqualTo("a String");

    json = script.getVariable("booleanVar");

    assertThat(json.isBoolean()).isTrue();
    assertThat(json.boolValue()).isFalse();

    json = script.getVariable("integerVar");

    assertThat(json.isNumber()).isTrue();
    assertThat(json.numberValue().intValue()).isEqualTo(42);

    SpinJsonNode jsonList = script.getVariable("listVar");

    assertThat(jsonList.isArray()).isTrue();
    SpinList<SpinJsonNode> elements = jsonList.elements();
    assertThat(elements.get(0).stringValue()).isEqualTo("Waldo");
    assertThat(elements.get(1).stringValue()).isEqualTo("Hugo");
    assertThat(elements.get(2).stringValue()).isEqualTo("Kermit");
  }
}
