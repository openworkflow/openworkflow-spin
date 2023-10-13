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
package co.openworkflow.spin.impl.xml.dom.query;

import static co.openworkflow.spin.impl.xml.dom.util.DomXmlEnsure.ensureNotDocumentRootExpression;
import static co.openworkflow.spin.impl.xml.dom.util.DomXmlEnsure.ensureXPathNotEmpty;
import static co.openworkflow.spin.impl.xml.dom.util.DomXmlEnsure.ensureXPathNotNull;

import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import co.openworkflow.spin.SpinList;
import co.openworkflow.spin.impl.SpinListImpl;
import co.openworkflow.spin.impl.xml.dom.DomXmlAttributeIterable;
import co.openworkflow.spin.impl.xml.dom.DomXmlElement;
import co.openworkflow.spin.impl.xml.dom.DomXmlElementIterable;
import co.openworkflow.spin.impl.xml.dom.DomXmlLogger;
import co.openworkflow.spin.impl.xml.dom.format.DomXmlDataFormat;
import co.openworkflow.spin.xml.SpinXPathQuery;
import co.openworkflow.spin.xml.SpinXmlAttribute;
import co.openworkflow.spin.xml.SpinXmlElement;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author Sebastian Menski
 */
public class DomXPathQuery extends SpinXPathQuery {

  private static final DomXmlLogger LOG = DomXmlLogger.XML_DOM_LOGGER;
  protected final DomXmlElement domElement;
  protected final XPath query;
  protected final String expression;
  protected final DomXmlDataFormat dataFormat;
  protected DomXPathNamespaceResolver resolver;

  public DomXPathQuery(DomXmlElement domElement, XPath query, String expression, DomXmlDataFormat dataFormat) {
    this.domElement = domElement;
    this.query = query;
    this.expression = expression;
    this.dataFormat = dataFormat;
    this.resolver = new DomXPathNamespaceResolver(this.domElement);

    this.query.setNamespaceContext(this.resolver);
  }

  public SpinXmlElement element() {
    try {
      ensureNotDocumentRootExpression(expression);
      Element element = (Element) query.evaluate(expression, domElement.unwrap(), XPathConstants.NODE);
      ensureXPathNotNull(element, expression);
      return dataFormat.createElementWrapper(element);
    } catch (XPathExpressionException e) {
      throw LOG.unableToEvaluateXPathExpressionOnElement(domElement, e);
    } catch (ClassCastException e) {
      throw LOG.unableToCastXPathResultTo(Element.class, e);
    }
  }

  public SpinList<SpinXmlElement> elementList() {
    try {
      ensureNotDocumentRootExpression(expression);
      NodeList nodeList = (NodeList) query.evaluate(expression, domElement.unwrap(), XPathConstants.NODESET);
      ensureXPathNotEmpty(nodeList, expression);
      return new SpinListImpl<SpinXmlElement>(new DomXmlElementIterable(nodeList, dataFormat));
    } catch (XPathExpressionException e) {
      throw LOG.unableToEvaluateXPathExpressionOnElement(domElement, e);
    } catch (ClassCastException e) {
      throw LOG.unableToCastXPathResultTo(NodeList.class, e);
    }
  }

  public SpinXmlAttribute attribute() {
    try {
      ensureNotDocumentRootExpression(expression);
      Attr attribute = (Attr) query.evaluate(expression, domElement.unwrap(), XPathConstants.NODE);
      ensureXPathNotNull(attribute, expression);
      return dataFormat.createAttributeWrapper(attribute);
    } catch (XPathExpressionException e) {
      throw LOG.unableToEvaluateXPathExpressionOnElement(domElement, e);
    } catch (ClassCastException e) {
      throw LOG.unableToCastXPathResultTo(Attr.class, e);
    }
  }

  public SpinList<SpinXmlAttribute> attributeList() {
    try {
      ensureNotDocumentRootExpression(expression);
      NodeList nodeList = (NodeList) query.evaluate(expression, domElement.unwrap(), XPathConstants.NODESET);
      ensureXPathNotEmpty(nodeList, expression);
      return new SpinListImpl<SpinXmlAttribute>(new DomXmlAttributeIterable(nodeList, dataFormat));
    } catch (XPathExpressionException e) {
      throw LOG.unableToEvaluateXPathExpressionOnElement(domElement, e);
    } catch (ClassCastException e) {
      throw LOG.unableToCastXPathResultTo(NodeList.class, e);
    }
  }

  public String string() {
    try {
      ensureNotDocumentRootExpression(expression);
      return (String) query.evaluate(expression, domElement.unwrap(), XPathConstants.STRING);
    } catch (XPathExpressionException e) {
      throw LOG.unableToEvaluateXPathExpressionOnElement(domElement, e);
    } catch (ClassCastException e) {
      throw LOG.unableToCastXPathResultTo(String.class, e);
    }
  }

  public Double number() {
    try {
      ensureNotDocumentRootExpression(expression);
      return (Double) query.evaluate(expression, domElement.unwrap(), XPathConstants.NUMBER);
    } catch (XPathExpressionException e) {
      throw LOG.unableToEvaluateXPathExpressionOnElement(domElement, e);
    } catch (ClassCastException e) {
      throw LOG.unableToCastXPathResultTo(Double.class, e);
    }
  }

  public Boolean bool() {
    try {
      ensureNotDocumentRootExpression(expression);
      return (Boolean) query.evaluate(expression, domElement.unwrap(), XPathConstants.BOOLEAN);
    } catch (XPathExpressionException e) {
      throw LOG.unableToEvaluateXPathExpressionOnElement(domElement, e);
    } catch (ClassCastException e) {
      throw LOG.unableToCastXPathResultTo(Boolean.class, e);
    }
  }

  public SpinXPathQuery ns(String prefix, String namespace) {
    resolver.setNamespace(prefix, namespace);
    query.setNamespaceContext(resolver);
    return this;
  }

  public SpinXPathQuery ns(Map<String, String> namespaces) {
    resolver.setNamespaces(namespaces);
    query.setNamespaceContext(resolver);
    return this;
  }

}
