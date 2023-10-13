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
package co.openworkflow.spin.json.tree.type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static co.openworkflow.spin.DataFormats.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import co.openworkflow.spin.DataFormats;
import co.openworkflow.spin.impl.json.jackson.format.JacksonJsonDataFormat;
import co.openworkflow.spin.impl.json.jackson.format.MapJacksonJsonTypeDetector;
import co.openworkflow.spin.impl.json.jackson.format.SetJacksonJsonTypeDetector;
import co.openworkflow.spin.json.mapping.Customer;
import co.openworkflow.spin.json.mapping.RegularCustomer;
import org.junit.Before;
import org.junit.Test;

public class JsonJacksonTreeTypeDetectionTest {

  public JacksonJsonDataFormat dataFormatWithSetTypeDetector =
      new JacksonJsonDataFormat(DataFormats.JSON_DATAFORMAT_NAME);

  public JacksonJsonDataFormat dataFormatWithMapTypeDetector =
      new JacksonJsonDataFormat(DataFormats.JSON_DATAFORMAT_NAME);

  @Before
  public void configure() {
    dataFormatWithSetTypeDetector.addTypeDetector(SetJacksonJsonTypeDetector.INSTANCE);
    dataFormatWithMapTypeDetector.addTypeDetector(MapJacksonJsonTypeDetector.INSTANCE);
  }

  @Test
  public void shouldDetectTypeFromObject() {
    RegularCustomer customer = new RegularCustomer();
    String canonicalTypeString = json().getMapper().getCanonicalTypeName(customer);
    assertThat(canonicalTypeString).isEqualTo("co.openworkflow.spin.json.mapping.RegularCustomer");
  }

  @Test
  public void shouldDetectListType() {
    List<Customer> customers = new ArrayList<>();
    customers.add(new RegularCustomer());

    String canonicalTypeString = json().getMapper().getCanonicalTypeName(customers);
    assertThat(canonicalTypeString).isEqualTo("java.util.ArrayList<co.openworkflow.spin.json.mapping.RegularCustomer>");
  }

  @Test
  public void shouldDetectListTypeFromEmptyList() {
    List<RegularCustomer> customers = new ArrayList<>();

    String canonicalTypeString = json().getMapper().getCanonicalTypeName(customers);
    assertThat(canonicalTypeString).isEqualTo("java.util.ArrayList<java.lang.Object>");
  }

  @Test
  public void shouldDetectSetType() {
    Set<Customer> customers = new HashSet<>();
    customers.add(new RegularCustomer());

    String canonicalTypeString = dataFormatWithSetTypeDetector.getCanonicalTypeName(customers);
    assertThat(canonicalTypeString).isEqualTo("java.util.HashSet<co.openworkflow.spin.json.mapping.RegularCustomer>");
  }

  @Test
  public void shouldDetectSetTypeFromEmptySet() {
    Set<RegularCustomer> customers = new HashSet<>();

    String canonicalTypeString = dataFormatWithSetTypeDetector.getCanonicalTypeName(customers);
    assertThat(canonicalTypeString).isEqualTo("java.util.HashSet<java.lang.Object>");
  }

  @Test
  public void shouldDetectMapType() {
    Map<String, Customer> customers = new HashMap<>();
    customers.put("foo", new RegularCustomer());

    String canonicalTypeString = dataFormatWithMapTypeDetector.getCanonicalTypeName(customers);
    assertThat(canonicalTypeString).isEqualTo("java.util.Map<java.lang.String,co.openworkflow.spin.json.mapping.RegularCustomer>");
  }

  @Test
  public void shouldDetectMapTypeFromEmptyMap() {
    Map<Integer, RegularCustomer> customers = new HashMap<>();

    String canonicalTypeString = dataFormatWithMapTypeDetector.getCanonicalTypeName(customers);
    assertThat(canonicalTypeString).isEqualTo("java.util.HashMap<java.lang.Object,java.lang.Object>");
  }

  @Test
  public void shouldHandleNullParameter() {
    try {
      json().getMapper().getCanonicalTypeName(null);
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // happy path
    }
  }

  @Test
  public void shouldHandleListOfLists() {
    List<List<RegularCustomer>> nestedCustomers = new ArrayList<>();
    List<RegularCustomer> customers = new ArrayList<>();
    customers.add(new RegularCustomer());
    nestedCustomers.add(customers);

    String canonicalTypeString = json().getMapper().getCanonicalTypeName(nestedCustomers);
    assertThat(canonicalTypeString).isEqualTo("java.util.ArrayList<java.util.ArrayList<co.openworkflow.spin.json.mapping.RegularCustomer>>");
  }

  @Test
  public void shouldHandleSetOfSets() {
    Set<Set<RegularCustomer>> nestedCustomers = new HashSet<>();
    Set<RegularCustomer> customers = new HashSet<>();
    customers.add(new RegularCustomer());
    nestedCustomers.add(customers);

    String canonicalTypeString =
        dataFormatWithSetTypeDetector.getCanonicalTypeName(nestedCustomers);
    assertThat(canonicalTypeString).isEqualTo("java.util.HashSet<java.util.HashSet<co.openworkflow.spin.json.mapping.RegularCustomer>>");
  }

  @Test
  public void shouldHandleSetOfLists() {
    Set<List<RegularCustomer>> nestedCustomers = new HashSet<>();
    List<RegularCustomer> customers = new ArrayList<>();
    customers.add(new RegularCustomer());
    nestedCustomers.add(customers);

    String canonicalTypeString =
        dataFormatWithSetTypeDetector.getCanonicalTypeName(nestedCustomers);
    assertThat(canonicalTypeString).isEqualTo("java.util.HashSet<java.util.ArrayList<co.openworkflow.spin.json.mapping.RegularCustomer>>");
  }

  @Test
  public void shouldHandleMapOfMaps() {
    Map<String, Map<Integer, RegularCustomer>> nestedCustomers = new HashMap<>();
    Map<Integer, RegularCustomer> customers = new HashMap<>();
    customers.put(42, new RegularCustomer());
    nestedCustomers.put("foo", customers);

    String canonicalTypeString =
        dataFormatWithMapTypeDetector.getCanonicalTypeName(nestedCustomers);
    assertThat(canonicalTypeString).isEqualTo("java.util.Map<java.lang.String,java.util.Map<java.lang.Integer,co.openworkflow.spin.json.mapping.RegularCustomer>>");
  }

  @Test
  public void shouldHandleMapWithNullAndStringValue() {
    Map<String, Object> map = new HashMap<>();
    map.put("bar", null);
    map.put("foo", "baz");

    String canonicalTypeString = dataFormatWithMapTypeDetector.getCanonicalTypeName(map);
    assertThat(canonicalTypeString).isEqualTo("java.util.Map<java.lang.String,java.lang.String>");
  }

  @Test
  public void shouldHandleMapWithNullAndNullValue() {
    Map<String, Object> map = new HashMap<>();
    map.put("foo", null);
    map.put("bar", null);

    String canonicalTypeString = dataFormatWithMapTypeDetector.getCanonicalTypeName(map);
    assertThat(canonicalTypeString).isEqualTo("java.util.Map<java.lang.String,java.lang.Object>");
  }

  @Test
  public void shouldHandleMapWithSingleNullValue() {
    Map<String, Object> map = new HashMap<>();
    map.put("bar", null);

    String canonicalTypeString = dataFormatWithMapTypeDetector.getCanonicalTypeName(map);
    assertThat(canonicalTypeString).isEqualTo("java.util.Map<java.lang.String,java.lang.Object>");
  }

  @Test
  public void shouldHandleSetWithSingleStringValue() {
    Set<String> set = new HashSet<>();
    set.add("foo");

    String canonicalTypeString = dataFormatWithSetTypeDetector.getCanonicalTypeName(set);
    assertThat(canonicalTypeString).isEqualTo("java.util.HashSet<java.lang.String>");
  }

  @Test
  public void shouldHandleSetWithNullAndStringValue() {
    Set<Object> set = new HashSet<>();
    set.add(null);
    set.add("foo");

    String canonicalTypeString = dataFormatWithSetTypeDetector.getCanonicalTypeName(set);
    assertThat(canonicalTypeString).isEqualTo("java.util.HashSet<java.lang.String>");
  }

  @Test
  public void shouldHandleSetWithNullValue() {
    Set<String> set = new HashSet<>();
    set.add(null);

    String canonicalTypeString = dataFormatWithSetTypeDetector.getCanonicalTypeName(set);
    assertThat(canonicalTypeString).isEqualTo("java.util.HashSet<java.lang.Object>");
  }

}
