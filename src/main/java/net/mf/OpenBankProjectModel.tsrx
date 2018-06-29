<?xml version="1.0" encoding="utf-8" standalone="yes"?>
<applicationModel>
    <name>Application Model</name>
    <className>OpenBankProjectModel</className>
    <objects>
        <object type="Page" technology="Web">
            <name>Open Bank Project: Login</name>
            <codeName>openBankProjectLoginPage</codeName>
            <description></description>
            <identification/>
            <children>
                <object type="EditField" technology="Web">
                    <name>username</name>
                    <codeName>usernameEditField</codeName>
                    <description></description>
                    <identification>
                        <properties>
                            <property name="Name" type="string" isRegExp="false">username</property>
                            <property name="TagName" type="string" isRegExp="false">INPUT</property>
                            <property name="Type" type="string" isRegExp="false">text</property>
                        </properties>
                    </identification>
                </object>
                <object type="EditField" technology="Web">
                    <name>password</name>
                    <codeName>passwordEditField</codeName>
                    <description></description>
                    <identification>
                        <properties>
                            <property name="Name" type="string" isRegExp="false">password</property>
                            <property name="TagName" type="string" isRegExp="false">INPUT</property>
                            <property name="Type" type="string" isRegExp="false">password</property>
                        </properties>
                    </identification>
                </object>
                <object type="Button" technology="Web">
                    <name>Login</name>
                    <codeName>loginButton</codeName>
                    <description></description>
                    <identification>
                        <properties>
                            <property name="ButtonType" type="string" isRegExp="false">submit</property>
                            <property name="Name" type="string" isRegExp="false">Login</property>
                            <property name="TagName" type="string" isRegExp="false">INPUT</property>
                        </properties>
                    </identification>
                </object>
            </children>
        </object>
        <object type="Page" technology="Web">
            <name>Open Bank Project: Home</name>
            <codeName>openBankProjectHomePage</codeName>
            <description></description>
            <identification/>
            <children>
                <object type="Link" technology="Web">
                    <name>Login</name>
                    <codeName>loginLink</codeName>
                    <description></description>
                    <identification>
                        <properties>
                            <property name="InnerText" type="string" isRegExp="false">Login</property>
                            <property name="TagName" type="string" isRegExp="false">A</property>
                        </properties>
                    </identification>
                </object>
                <object type="Link" technology="Web">
                    <name>Logout corndog</name>
                    <codeName>logoutLink</codeName>
                    <description></description>
                    <identification>
                        <properties>
                            <property name="InnerText" type="string" isRegExp="true">Logout .*</property>
                            <property name="TagName" type="string" isRegExp="false">A</property>
                            <property name="ClassName" type="string" isRegExp="false">btn btn-default logout</property>
                        </properties>
                    </identification>
                </object>
                <object type="Link" technology="Web">
                    <name>Create Bank Account</name>
                    <codeName>createBankAccountLink</codeName>
                    <description></description>
                    <identification>
                        <properties>
                            <property name="InnerText" type="string" isRegExp="false">Create Bank Account</property>
                            <property name="TagName" type="string" isRegExp="false">A</property>
                        </properties>
                    </identification>
                </object>
            </children>
        </object>
        <object type="Page" technology="Web">
            <name>Open Bank Project: Create Bank Account</name>
            <codeName>openBankProjectCreateBankAccountPage</codeName>
            <description></description>
            <identification/>
            <children>
                <object type="ListBox" technology="Web">
                    <name>Bank List Box</name>
                    <codeName>BankListBox</codeName>
                    <description></description>
                    <identification>
                        <properties>
                            <property name="TagName" type="string" isRegExp="false">SELECT</property>
                            <property name="Id" type="string" isRegExp="false">create-account-bank-selector</property>
                        </properties>
                    </identification>
                </object>
                <object type="EditField" technology="Web">
                    <name>Account Id Field</name>
                    <codeName>DesiredAccountIdEditField</codeName>
                    <description></description>
                    <identification>
                        <properties>
                            <property name="TagName" type="string" isRegExp="false">INPUT</property>
                            <property name="Type" type="string" isRegExp="false">text</property>
                            <property name="XPath" type="string" isRegExp="false">//div[2]/input</property>
                        </properties>
                    </identification>
                </object>
                <object type="EditField" technology="Web">
                    <name>Currency Field</name>
                    <codeName>DesiredCurrencyEditField</codeName>
                    <description></description>
                    <identification>
                        <properties>
                            <property name="TagName" type="string" isRegExp="false">INPUT</property>
                            <property name="Type" type="string" isRegExp="false">text</property>
                            <property name="XPath" type="string" isRegExp="false">//DIV[3]/INPUT[1]</property>
                        </properties>
                    </identification>
                </object>
                <object type="EditField" technology="Web">
                    <name>Initial Balance</name>
                    <codeName>DesiredInitialBalanceEditField</codeName>
                    <description></description>
                    <identification>
                        <properties>
                            <property name="TagName" type="string" isRegExp="false">INPUT</property>
                            <property name="Type" type="string" isRegExp="false">text</property>
                            <property name="XPath" type="string" isRegExp="false">//DIV[4]/INPUT[1]</property>
                        </properties>
                    </identification>
                </object>
                <object type="Button" technology="Web">
                    <name>Create Account</name>
                    <codeName>createAccountButton</codeName>
                    <description></description>
                    <identification>
                        <properties>
                            <property name="ButtonType" type="string" isRegExp="false">submit</property>
                            <property name="Name" type="string" isRegExp="false">Create Account</property>
                            <property name="TagName" type="string" isRegExp="false">INPUT</property>
                        </properties>
                    </identification>
                </object>
            </children>
        </object>
    </objects>
</applicationModel>
