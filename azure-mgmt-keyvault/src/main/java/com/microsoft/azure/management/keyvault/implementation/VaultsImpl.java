/**
  * Copyright (c) Microsot Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.graphrbac.implementation.GraphRbacManager;
import com.microsoft.azure.management.keyvault.DeletedVault;
import com.microsoft.azure.management.keyvault.SkuName;
import com.microsoft.azure.management.keyvault.Vault;
import com.microsoft.azure.management.keyvault.VaultProperties;
import com.microsoft.azure.management.keyvault.Vaults;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.GroupableResourcesImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.PagedListConverter;

import rx.Completable;
import rx.Observable;

import java.util.UUID;

/**
 * The implementation of Vaults and its parent interfaces.
 */
@LangDefinition
class VaultsImpl
        extends GroupableResourcesImpl<
            Vault,
            VaultImpl,
            VaultInner,
            VaultsInner,
            KeyVaultManager>
        implements Vaults {
    private final GraphRbacManager graphRbacManager;
    private final String tenantId;

    VaultsImpl(
            final KeyVaultManager keyVaultManager,
            final GraphRbacManager graphRbacManager,
            final String tenantId) {
        super(keyVaultManager.inner().vaults(), keyVaultManager);
        this.graphRbacManager = graphRbacManager;
        this.tenantId = tenantId;
    }

    
    @Override
    public PagedList<Vault> listByResourceGroup(String groupName) {
        return wrapList(this.inner().listByResourceGroup(groupName));
    }

    @Override
    protected Observable<VaultInner> getInnerAsync(String resourceGroupName, String name) {
        return this.inner().getByResourceGroupAsync(resourceGroupName, name);
    }

    @Override
    protected Completable deleteInnerAsync(String resourceGroupName, String name) {
        return this.inner().deleteAsync(resourceGroupName, name).toCompletable();
    }

    @Override
    public Completable deleteByResourceGroupAsync(String groupName, String name) {
        return this.inner().deleteAsync(groupName, name).toCompletable();
    }

    @Override
    public VaultImpl define(String name) {
        return wrapModel(name)
                .withSku(SkuName.STANDARD)
                .withEmptyAccessPolicy();
    }

    @Override
    protected VaultImpl wrapModel(String name) {
        VaultInner inner = new VaultInner().withProperties(new VaultProperties());
        inner.properties().withTenantId(UUID.fromString(tenantId));
        return new VaultImpl(
                name,
                inner,
                this.manager(),
                graphRbacManager);
    }

    @Override
    protected VaultImpl wrapModel(VaultInner vaultInner) {
        if (vaultInner == null) {
            return null;
        }
        return new VaultImpl(
                vaultInner.name(),
                vaultInner,
                super.manager(),
                graphRbacManager);
    }

    @Override
    public Observable<Vault> listByResourceGroupAsync(String resourceGroupName) {
        return wrapPageAsync(this.inner().listByResourceGroupAsync(resourceGroupName));
    }
	
	@Override
	public PagedList<DeletedVault> listDeleted() {
		return wrapPage(this.inner().listDeleted());
	}

	//Hacky method to convert from DeletedVaultInner to DeletedVault
	private PagedList<DeletedVault> wrapPage(PagedList<DeletedVaultInner> listDeleted) {
		PagedListConverter<DeletedVaultInner, DeletedVault> converter =  
				new PagedListConverter<DeletedVaultInner, DeletedVault> () {
					@Override
					public Observable<DeletedVault> typeConvertAsync(DeletedVaultInner inner) {
						return Observable.just((DeletedVault) new DeletedVaultImpl(inner));
					}
		};
		return converter.convert(listDeleted);
	}

	@Override
	public DeletedVault getDeleted(String vaultName, String location) {
	    Object deletedVault = inner().getDeleted(vaultName, location);
		if (deletedVault == null) {
			return null;
		}
		return new DeletedVaultImpl((DeletedVaultInner) deletedVault);
	}

	@Override
	public void purgeDeleted(String vaultName, String location) {
		inner().purgeDeleted(vaultName, location);
	}

}
