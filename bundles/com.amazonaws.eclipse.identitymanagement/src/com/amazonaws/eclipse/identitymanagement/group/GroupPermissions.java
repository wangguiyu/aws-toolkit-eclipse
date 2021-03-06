/*
 * Copyright 2013 Amazon Technologies, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *    http://aws.amazon.com/apache2.0
 *
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and
 * limitations under the License.
 */
package com.amazonaws.eclipse.identitymanagement.group;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.amazonaws.eclipse.core.AwsToolkitCore;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.Group;

public class GroupPermissions extends Composite {

    private Group group;
    private GroupPermissionTable groupPermissionTable;
    private Button addPolicyButton;


    public GroupPermissions(final AmazonIdentityManagement iam, Composite parent, final FormToolkit toolkit) {
        super(parent, SWT.NONE);

        this.setLayoutData(new GridData(GridData.FILL_BOTH));
        this.setBackground(toolkit.getColors().getBackground());

        this.setLayout(new GridLayout());

        Section policySection = toolkit.createSection(this, Section.TITLE_BAR);
        policySection.setText("Group Policies");
        policySection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        Composite client = toolkit.createComposite(policySection, SWT.WRAP);
        client.setLayoutData(new GridData(GridData.FILL_BOTH));
        client.setLayout(new GridLayout(2, false));

        groupPermissionTable = new GroupPermissionTable(iam, client, toolkit);
        groupPermissionTable.setLayoutData(new GridData(GridData.FILL_BOTH));


        addPolicyButton = toolkit.createButton(client, "Attach Policy", SWT.PUSH);

        addPolicyButton.setEnabled(false);
        addPolicyButton.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
        addPolicyButton.setImage(AwsToolkitCore.getDefault().getImageRegistry().get(AwsToolkitCore.IMAGE_ADD));
        addPolicyButton.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                AddGroupPolicyDialog addGroupPolicyDialog = new AddGroupPolicyDialog(iam, Display.getCurrent().getActiveShell(), toolkit, group, groupPermissionTable);
                addGroupPolicyDialog.open();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });

        policySection.setClient(client);
    }

    public void setGroup(Group group) {
        this.group = group;
        if (group != null) {
            addPolicyButton.setEnabled(true);
        } else {
            addPolicyButton.setEnabled(false);
        }
        groupPermissionTable.setGroup(group);
    }

}
