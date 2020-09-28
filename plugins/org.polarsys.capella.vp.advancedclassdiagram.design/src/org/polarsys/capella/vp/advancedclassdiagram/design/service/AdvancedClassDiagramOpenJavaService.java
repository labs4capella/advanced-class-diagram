
// Generated on 02.01.2019 at 01:03:12 CET by Viewpoint DSL Generator V 0.1

package org.polarsys.capella.vp.advancedclassdiagram.design.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DEdge;
import org.polarsys.capella.core.data.capellacore.GeneralizableElement;
import org.polarsys.capella.core.data.information.Association;
import org.polarsys.capella.core.data.information.Class;
import org.polarsys.capella.core.data.information.ExchangeItem;
import org.polarsys.capella.core.data.information.ExchangeItemElement;
import org.polarsys.capella.core.data.information.Operation;
import org.polarsys.capella.core.data.information.Property;
import org.polarsys.capella.core.data.information.datavalue.DataValue;
import org.polarsys.capella.core.sirius.analysis.CapellaServices;


/**
 * <!-- begin-user-doc -->
 * This class is an implementation of the Sirius JavaExtension '<em><b>[org.polarsys.capella.vp.advancedclassdiagram.design.service.AdvancedClassDiagramOpenJavaService]</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */

public class AdvancedClassDiagramOpenJavaService {
	/**
	* <!-- begin-user-doc -->
	* <!-- end-user-doc -->
	* @generated
	*/
	public AdvancedClassDiagramOpenJavaService() {
		// TODO Auto-generated method stub
	}
	
	public Collection<EObject> getListofInheritedProperties (EObject elem, DDiagram diagram) {
		Collection<EObject> result = new ArrayList<EObject>();
		Collection<EObject> representedElements = getAllRepresentedElements(diagram);
		CapellaServices service = new CapellaServices();
		if (elem instanceof Class) {
			Class currentClass = (Class) elem;
			for (GeneralizableElement superElem : getListofHiddenSuperTypes(currentClass, representedElements)) {
				Class superClass = (Class) superElem;
				for (Property prop : superClass.getContainedProperties()) {
					// this check is performed in regular class diagram
					// removes the associations which are also stored in the model as properties
					if (service.isPrimitiveTypeForAttribute(prop)) {
						result.add(prop);
					}
				}
			}
		}
		return result;
	}
	
	public Collection<EObject> getListofInheritedOperations (EObject elem, DDiagram diagram) {
		Collection<EObject> result = new ArrayList<EObject>();
		Collection<EObject> representedElements = getAllRepresentedElements(diagram);
		if (elem instanceof Class) {
			Class currentClass = (Class) elem;
			for (GeneralizableElement superElem : getListofHiddenSuperTypes(currentClass, representedElements)) {
				Class superClass = (Class) superElem;
				for (Operation op : superClass.getContainedOperations()) {
					result.add(op);
				}
			}
		}
		return result;
	}
	
	public Collection<EObject> getListofInheritedDataValues (EObject elem, DDiagram diagram) {
		Collection<EObject> result = new ArrayList<EObject>();
		Collection<EObject> representedElements = getAllRepresentedElements(diagram);
		if (elem instanceof Class) {
			Class currentClass = (Class) elem;
			for (GeneralizableElement superElem : getListofHiddenSuperTypes(currentClass, representedElements)) {
				Class superClass = (Class) superElem;
				for (DataValue val : superClass.getOwnedDataValues()) {
					result.add(val);
				}
			}
		}
		return result;
	}
	
	public Collection<EObject> getAllRepresentedElements(DDiagram diagram) {
		Collection<EObject> result = new ArrayList<EObject>();
		for (DDiagramElement elem : diagram.getDiagramElements()) {
			if (elem.getTarget() instanceof EObject) {
				result.add(elem.getTarget());
			}
		}
		return result;
	}

	public Collection<GeneralizableElement> getListofHiddenSuperTypes (GeneralizableElement currentElem, Collection<EObject> representedElements) {
		Collection<GeneralizableElement> result = new ArrayList<GeneralizableElement>();
		for (GeneralizableElement superElem : currentElem.getSuper()) {
			if (!representedElements.contains(superElem)) {
				// first deal with super classes of super class
				result.addAll(getListofHiddenSuperTypes(superElem, representedElements));
				// then deal with super class itself
				if (!result.contains(superElem)) {
					result.add(superElem);
				}
			}
		}
		return result;
	}
	
	public String classPrefix (EObject context) {
		Class parentClass = (Class) context.eContainer();
		return "["+parentClass.getName()+"] ";
	}
	
	public Collection<EObject> getListofComputedGeneralizations (EObject elem, DDiagram diagram) {
		Collection<EObject> result = new ArrayList<EObject>();
		Collection<EObject> representedElems = getAllRepresentedElements(diagram);
		if (elem instanceof GeneralizableElement) {
			GeneralizableElement currentElem = (GeneralizableElement) elem;
			for (GeneralizableElement superElem : currentElem.getSuper()) {
				if (!representedElems.contains(superElem)) {
					result.addAll(getFirstRepresentedElem(superElem, representedElems));
				}
			}
		}
		return result;
	}
	
	public Collection<EObject> getListofComputedAssociations (DDiagram diagram) {
		Collection<EObject> result = new ArrayList<EObject>();
		Collection<EObject> representedElems = getAllRepresentedElements(diagram);
		CapellaServices service = new CapellaServices();
		for (EObject elem : representedElems) {
			// associations only start from classes
			if (elem instanceof Class) {
				Class currentClass = (Class) elem;
				for (GeneralizableElement superElem : getListofHiddenSuperTypes(currentClass, representedElems)) {
					Class superClass = (Class) superElem;
					for (Property prop : superClass.getContainedProperties()) {
						// we want to find non primitive types => related to associations
						if (!service.isPrimitiveTypeForAttribute(prop)) {
							// we want to have the target of the association (= the type of the property) visible in the diagram
							if (representedElems.contains(prop.getAbstractType())) {
								//we want to check that there is indeed an association related to this property
								if (prop.getAssociation() != null) {
									result.add(prop.getAssociation());
								}
							}
						}
					}
				}
			}
		}
		return result;
	}
	
	public EObject getComputedAssociationTarget (EObject elem) {
		if (elem instanceof Association) {
			Association association = (Association) elem;
			return association.getNavigableMembers().get(0).getAbstractType();
		}
		return null;
	}
	
	public Collection<EObject> getComputedAssociationSources (EObject elem, DDiagram diagram) {
		Collection<EObject> result = new ArrayList<EObject>();
		Collection<EObject> representedElems = getAllRepresentedElements(diagram);
		if (elem instanceof Association) {
			Association association = (Association) elem;
			for (Property prop : association.getNavigableMembers()) {
				GeneralizableElement parent = (GeneralizableElement) prop.eContainer();
				result.addAll(getFirstsVisibleSubElem(parent, representedElems));
			}
		}
		return result;
	}
	
	public Collection<EObject> getListofComputedEIE (DDiagram diagram) {
		Collection<EObject> result = new ArrayList<EObject>();
		Collection<EObject> representedElems = getAllRepresentedElements(diagram);
		for (EObject elem : representedElems) {
			// EIE only start from ExchangeItems
			if (elem instanceof ExchangeItem) {
				ExchangeItem currentEI = (ExchangeItem) elem;
				for (GeneralizableElement superElem : getListofHiddenSuperTypes(currentEI, representedElems)) {
					ExchangeItem superEI = (ExchangeItem) superElem;
					for (ExchangeItemElement EIE : superEI.getOwnedElements()) {
						// we want to have the target of the EIE visible in the diagram
						if (representedElems.contains(EIE.getAbstractType())) {
							result.add(EIE);
						}
					}
				}
			}
		}
		return result;
	}
	
	public Collection<EObject> getComputedEIESources (EObject elem, DDiagram diagram) {
		Collection<EObject> result = new ArrayList<EObject>();
		Collection<EObject> representedElems = getAllRepresentedElements(diagram);
		if (elem instanceof ExchangeItemElement) {
			ExchangeItemElement EIE = (ExchangeItemElement) elem;
			GeneralizableElement parent = (GeneralizableElement) EIE.eContainer();
			result.addAll(getFirstsVisibleSubElem(parent, representedElems));
		}
		return result;
	}
	
	public Collection<GeneralizableElement> getFirstsVisibleSubElem (GeneralizableElement elem, Collection<EObject> representedElems) {
		Collection<GeneralizableElement> result = new ArrayList<GeneralizableElement>();
		for (GeneralizableElement subElem : elem.getSub()) {
			if (representedElems.contains(subElem)) {
				result.add(subElem);
			} else {
				result.addAll(getFirstsVisibleSubElem(subElem, representedElems));
			}
		}
		return result;
	}
	
	public Collection<EObject> getFirstRepresentedElem (GeneralizableElement currentElem, Collection<EObject> representedClasses) {
		Collection<EObject> result = new ArrayList<EObject>();
		for (GeneralizableElement superElem : currentElem.getSuper()) {
			if (representedClasses.contains(superElem)) {
				result.add(superElem);
			} else {
				result.addAll(getFirstRepresentedElem(superElem, representedClasses));
			}
		}
		return result;
	}
	
	public String getComputedGeneralizationLabel(DDiagramElement view) {
		StringBuffer result = new StringBuffer();
		DDiagram diagram = view.getParentDiagram();
		Collection<EObject> representedElements = getAllRepresentedElements(diagram);
		if (view instanceof DEdge) {
			result.append("through ");
			DEdge edge = (DEdge) view;
			DDiagramElement sourceNode = (DDiagramElement) edge.getSourceNode();
			DDiagramElement targetNode = (DDiagramElement) edge.getTargetNode();
			GeneralizableElement sourceElem = (GeneralizableElement) sourceNode.getTarget();
			GeneralizableElement targetElem = (GeneralizableElement) targetNode.getTarget();
			Collection<GeneralizableElement> transientElements = getAllHiddenTransientElements(sourceElem, targetElem, representedElements);
			Iterator<GeneralizableElement> iterator = transientElements.iterator();
			while (iterator.hasNext()) {
				GeneralizableElement currentElem = iterator.next();
				result.append("[");
				result.append(currentElem.getName());
				result.append("]");
				if (iterator.hasNext()) {
					result.append(", ");
				}
			}
		}
		return result.toString();
	}
	
	public Collection<GeneralizableElement> getAllHiddenTransientElements (GeneralizableElement currentElem, GeneralizableElement superElem, Collection<EObject> representedElements) {
		Collection<GeneralizableElement> result = new ArrayList<GeneralizableElement>();
		for (GeneralizableElement directSuperElem : currentElem.getSuper()) {
			if (isSuperElement(directSuperElem, superElem)) {
				if (!representedElements.contains(directSuperElem)) {
					result.add(directSuperElem);
				}
				result.addAll(getAllHiddenTransientElements(directSuperElem, superElem, representedElements));
			}
		}
		return result;
	}
	
	public Boolean isSuperElement (GeneralizableElement currentElem, GeneralizableElement superElem) {
		if (currentElem.getSuper().contains(superElem)) {
			return true;
		} else {
			Boolean found = false;
			for (GeneralizableElement directSuperElem : currentElem.getSuper()) {
				if (isSuperElement(directSuperElem, superElem)) {
					found = true;
					break;
				}
			}
			return found;
		}
	}
}