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
import static org.assertj.core.api.Assertions.fail;
import static co.openworkflow.spin.DataFormats.xml;
import static co.openworkflow.spin.Spin.S;
import static co.openworkflow.spin.Spin.XML;
import static co.openworkflow.spin.impl.util.SpinIoUtil.stringAsReader;
import static co.openworkflow.spin.xml.XmlTestConstants.EXAMPLE_EMPTY_STRING;
import static co.openworkflow.spin.xml.XmlTestConstants.EXAMPLE_INVALID_XML;
import static co.openworkflow.spin.xml.XmlTestConstants.EXAMPLE_XML;

import java.io.Reader;

import co.openworkflow.spin.DataFormats;
import co.openworkflow.spin.spi.SpinDataFormatException;
import co.openworkflow.spin.xml.SpinXmlElement;
import org.junit.Test;

/**
 * @author Daniel Meyer
 *
 */
public class XmlDomCreateTest {

  @Test
  public void shouldCreateForString() {
    SpinXmlElement xml = XML(EXAMPLE_XML);
    assertThat(xml).isNotNull();

    xml = S(EXAMPLE_XML, xml());
    assertThat(xml).isNotNull();

    xml = S(EXAMPLE_XML, DataFormats.XML_DATAFORMAT_NAME);
    assertThat(xml).isNotNull();

    xml = S(EXAMPLE_XML);
    assertThat(xml).isNotNull();
  }

  @Test
  public void shouldCreateForReader() {
    SpinXmlElement xml = XML(stringAsReader(EXAMPLE_XML));
    assertThat(xml).isNotNull();

    xml = S(stringAsReader(EXAMPLE_XML), xml());
    assertThat(xml).isNotNull();

    xml = S(stringAsReader(EXAMPLE_XML), DataFormats.XML_DATAFORMAT_NAME);
    assertThat(xml).isNotNull();

    xml = S(stringAsReader(EXAMPLE_XML));
    assertThat(xml).isNotNull();
  }

  @Test
  public void shouldBeIdempotent() {
    SpinXmlElement xml = XML(EXAMPLE_XML);
    assertThat(xml).isEqualTo(XML(xml));
    assertThat(xml).isEqualTo(S(xml, xml()));
    assertThat(xml).isEqualTo(S(xml, DataFormats.XML_DATAFORMAT_NAME));
    assertThat(xml).isEqualTo(S(xml));
  }

  @Test
  public void shouldFailForNull() {
    SpinXmlElement xmlTreeElement = null;

    try {
      XML(xmlTreeElement);
      fail("Expected IllegalArgumentException");
    } catch(IllegalArgumentException e) {
      // expected
    }

    try {
      S(xmlTreeElement, xml());
      fail("Expected IllegalArgumentException");
    } catch(IllegalArgumentException e) {
      // expected
    }

    try {
      S(xmlTreeElement);
      fail("Expected IllegalArgumentException");
    } catch(IllegalArgumentException e) {
      // expected
    }

    Reader reader = null;

    try {
      XML(reader);
      fail("Expected IllegalArgumentException");
    } catch(IllegalArgumentException e) {
      // expected
    }

    try {
      S(reader, xml());
      fail("Expected IllegalArgumentException");
    } catch(IllegalArgumentException e) {
      // expected
    }

    try {
      S(reader);
      fail("Expected IllegalArgumentException");
    } catch(IllegalArgumentException e) {
      // expected
    }

    String inputString = null;

    try {
      XML(inputString);
      fail("Expected IllegalArgumentException");
    } catch(IllegalArgumentException e) {
      // expected
    }

    try {
      S(inputString, xml());
      fail("Expected IllegalArgumentException");
    } catch(IllegalArgumentException e) {
      // expected
    }

    try {
      S(inputString, DataFormats.XML_DATAFORMAT_NAME);
      fail("Expected IllegalArgumentException");
    } catch(IllegalArgumentException e) {
      // expected
    }

    try {
      S(inputString);
      fail("Expected IllegalArgumentException");
    } catch(IllegalArgumentException e) {
      // expected
    }
  }

  @Test
  public void shouldFailForInvalidXml() {
    try {
      XML(EXAMPLE_INVALID_XML);
      fail("Expected IllegalArgumentException");
    } catch(SpinDataFormatException e) {
      // expected
    }

    try {
      S(EXAMPLE_INVALID_XML, xml());
      fail("Expected IllegalArgumentException");
    } catch(SpinDataFormatException e) {
      // expected
    }

    try {
      S(EXAMPLE_INVALID_XML, DataFormats.XML_DATAFORMAT_NAME);
      fail("Expected IllegalArgumentException");
    } catch(SpinDataFormatException e) {
      // expected
    }

    try {
      S(EXAMPLE_INVALID_XML);
      fail("Expected IllegalArgumentException");
    } catch(SpinDataFormatException e) {
      // expected
    }
  }

  @Test
  public void shouldFailForEmptyString() {
    try {
      XML(EXAMPLE_EMPTY_STRING);
      fail("Expected IllegalArgumentException");
    } catch(SpinDataFormatException e) {
      // expected
    }

    try {
      S(EXAMPLE_EMPTY_STRING, xml());
      fail("Expected IllegalArgumentException");
    } catch(SpinDataFormatException e) {
      // expected
    }

    try {
      S(EXAMPLE_EMPTY_STRING, DataFormats.XML_DATAFORMAT_NAME);
      fail("Expected IllegalArgumentException");
    } catch(SpinDataFormatException e) {
      // expected
    }

    try {
      S(EXAMPLE_EMPTY_STRING);
      fail("Expected IllegalArgumentException");
    } catch(SpinDataFormatException e) {
      // expected
    }
  }

  @Test
  public void shouldFailForEmptyReader() {
    try {
      XML(stringAsReader(EXAMPLE_EMPTY_STRING));
      fail("Expected IllegalArgumentException");
    } catch(SpinDataFormatException e) {
      // expected
    }

    try {
      S(stringAsReader(EXAMPLE_EMPTY_STRING), xml());
      fail("Expected IllegalArgumentException");
    } catch(SpinDataFormatException e) {
      // expected
    }

    try {
      S(stringAsReader(EXAMPLE_EMPTY_STRING));
      fail("Expected IllegalArgumentException");
    } catch(SpinDataFormatException e) {
      // expected
    }
  }
}
