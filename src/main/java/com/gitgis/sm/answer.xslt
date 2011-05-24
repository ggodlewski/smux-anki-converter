<xsl:stylesheet version="2.0"
	xmlns:sm="http://www.supermemo.net/2006/smux" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	xmlns:exer="com.gitgis.sm.ExerciseXsltUtils" 
	exclude-result-prefixes="sm exer java"
	extension-element-prefixes="exer">
	
	<xsl:output method="xml" omit-xml-declaration="yes" />
	<xsl:param name="exercise"/>
	<xsl:param name="itemId"/>

	<xsl:template match="/">
		<xsl:if test="not(//sm:answer)"><xsl:apply-templates select="//sm:question" /></xsl:if>
		<xsl:apply-templates select="//sm:answer" />
		<xsl:if test="//sm:answer-audio">[sound:<xsl:value-of select="$itemId"/>a.mp3]</xsl:if>
	</xsl:template>

	<xsl:template match="sm:answer|sm:question">
		<xsl:apply-templates />
	</xsl:template>

	<xsl:template match="sm:sfx">[sound:<xsl:value-of select="$itemId"/><xsl:value-of select="@file"/>.mp3]</xsl:template>

	<xsl:template match="sm:br"><br /></xsl:template>

	<xsl:template match="sm:table"><table><xsl:apply-templates/></table></xsl:template>
	<xsl:template match="sm:tr"><tr><xsl:apply-templates/></tr></xsl:template>
	<xsl:template match="sm:th"><th><xsl:apply-templates/></th></xsl:template>
	<xsl:template match="sm:td"><td><xsl:apply-templates/></td></xsl:template>
	<xsl:template match="sm:span"><span><xsl:if test="@style"><xsl:attribute name="style"><xsl:value-of select="@style"/></xsl:attribute></xsl:if><xsl:apply-templates/></span></xsl:template>

	<xsl:template match="sm:spellpad"><span style="color: red"><xsl:value-of select="@correct" /></span></xsl:template>
	<xsl:template match="sm:radio"><span style="color: red"><xsl:value-of select="sm:option[@correct]/text()"/></span></xsl:template>
	<xsl:template match="sm:droplist"><span style="color: red"><xsl:value-of select="sm:option[@correct]/text()"/></span></xsl:template>
	<xsl:template match="sm:ordering-list"><span style="color: red"><xsl:value-of select="exer:answerOrdering(sm:option/text())"/></span></xsl:template>
	<xsl:template match="sm:select-phrases">
		<xsl:if test="@mode='strikethrough'">
			<span style="color: red">[
				<xsl:for-each select="sm:option">
					<xsl:if test="@correct"><strike><xsl:value-of select="text()" />, </strike></xsl:if>
					<xsl:if test="not(@correct)"><xsl:value-of select="text()" />, </xsl:if>
				</xsl:for-each>
			]</span>
		</xsl:if>
		<xsl:if test="not(@mode='strikethrough')">
			<span style="color: red">[
				<xsl:for-each select="sm:option[@correct]/text()"><xsl:value-of select="." />, </xsl:for-each>
			]</span>
		</xsl:if>
	</xsl:template>
	<xsl:template match="sm:drag-drop">
		<xsl:value-of select="exer:answerDragDrop(sm:drop-text, sm:option/text(), 0)"/>
		<span style="color: red"><xsl:value-of select="exer:answerDragDrop(sm:drop-text, sm:option/text(), 1)"/></span>
		<xsl:value-of select="exer:answerDragDrop(sm:drop-text, sm:option/text(), 2)"/>
	</xsl:template>

</xsl:stylesheet>