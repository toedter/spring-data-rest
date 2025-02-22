/*
 * Copyright 2012-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.rest.tests.geode;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.gemfire.mapping.annotation.Region;
import org.springframework.util.Assert;

/**
 * A customer.
 *
 * @author Oliver Gierke
 * @author David Turanski
 * @author Mark Paluch
 */
@Region
public class Customer extends AbstractPersistentEntity {
	private EmailAddress emailAddress;
	private String firstname, lastname;
	private Set<Address> addresses = new HashSet<Address>();

	/**
	 * Creates a new {@link Customer} from the given parameters.
	 *
	 * @param id the unique id;
	 * @param emailAddress must not be {@literal null} or empty.
	 * @param firstname must not be {@literal null} or empty.
	 * @param lastname must not be {@literal null} or empty.
	 */
	public Customer(Long id, EmailAddress emailAddress, String firstname, String lastname) {
		super(id);
		Assert.hasText(firstname, "Firstname must not be null or empty!");
		Assert.hasText(lastname, "Lastname must not be null or empty!");
		Assert.notNull(emailAddress, "EmailAddress must not be null!");

		this.firstname = firstname;
		this.lastname = lastname;
		this.emailAddress = emailAddress;
	}

	protected Customer() {}

	/**
	 * Adds the given {@link Address} to the {@link Customer}.
	 *
	 * @param address must not be {@literal null}.
	 */
	public void add(Address address) {

		Assert.notNull(address, "Address must not be null!");
		this.addresses.add(address);
	}

	/**
	 * Returns the firstname of the {@link Customer}.
	 *
	 * @return
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Sets the firstname of the {@link Customer}.
	 *
	 * @param firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * Returns the lastname of the {@link Customer}.
	 *
	 * @return
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * Sets the lastname of the {@link Customer}.
	 *
	 * @param lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * Returns the {@link EmailAddress} of the {@link Customer}.
	 *
	 * @return
	 */
	public EmailAddress getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Sets the emailAddress of the {@link Customer}.
	 *
	 * @param emailAddress
	 */
	public void setEmailAddress(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Return the {@link Customer}'s addresses.
	 *
	 * @return
	 */
	public Set<Address> getAddresses() {
		return Collections.unmodifiableSet(addresses);
	}
}
