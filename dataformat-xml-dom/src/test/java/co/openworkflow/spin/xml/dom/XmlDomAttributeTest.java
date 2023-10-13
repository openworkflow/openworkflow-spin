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
import static co.openworkflow.spin.Spin.XML;

import java.io.StringWriter;

import co.openworkflow.spin.xml.SpinXmlAttribute;
import co.openworkflow.spin.xml.SpinXmlAttributeException;
import co.openworkflow.spin.xml.SpinXmlElement;
import co.openworkflow.spin.xml.XmlTestConstants;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Sebastian Menski
 */
public class XmlDomAttributeTest {

  private SpinXmlAttribute attribute;

  @Before
  public void getAttribute() {
    attribute = XML(XmlTestConstants.EXAMPLE_XML).attr("order");
  }

  @Test
  public void getValue() {
    assertThat(attribute.value()).isEqualTo("order1");
  }

  @Test
  public void getName() {
    assertThat(attribute.name()).isEqualTo("order");
  }

  @Test
  public void getNamespace() {
    assertThat(attribute.namespace()).isNull();
  }

  @Test
  public void hasNamespace() {
    assertThat(attribute.hasNamespace(null)).isTrue();
  }

  @Test
  public void setValue() {
    assertThat(attribute.value("order2").value()).isEqualTo("order2");
  }

  @Test(expected = SpinXmlAttributeException.class)
  public void setNullValue() {
    attribute.value(null);
  }

  @Test
  public void remove() {
    String namespace = attribute.namespace();
    String name = attribute.name();

    SpinXmlElement element = attribute.remove();
    assertThat(element.hasAttrNs(namespace, name)).isFalse();
  }

  // test io

  @Test
  public void canWriteToString() {
    assertThat(attribute.toString()).isEqualTo("order1");
  }

  @Test
  public void canWriteToWriter() {
    StringWriter writer = new StringWriter();
    attribute.writeToWriter(writer);
    String value = writer.toString();
    assertThat(value).isEqualTo("order1");
  }

}
