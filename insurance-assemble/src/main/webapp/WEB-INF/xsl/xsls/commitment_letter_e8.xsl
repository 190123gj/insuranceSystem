<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <!-- 根元素  -->


    <xsl:template match="/">
        <xsl:apply-templates select="EntryDailyListReport"/>
    </xsl:template>

    <!--主模板//-->
    <xsl:template match="EntryDailyListReport">
        <xsl:processing-instruction name="cocoon-format">type="text/xslfo"</xsl:processing-instruction>
        <!--在此可以定义一些全局的风格信息，如字体等-->
        <fo:root font-family="KaiTi_GB2312" xmlns:fo="http://www.w3.org/1999/XSL/Format">

            <!--版面定义//-->
            <fo:layout-master-set>
                <fo:simple-page-master
                    page-master-name="main"
                    margin-top="2cm"
                    margin-bottom="2cm"
                    margin-left="2cm"
                    margin-right="1.5cm">
                    <!--页眉//-->
                    <fo:region-before extent="0cm"/>
                    <!--主体//-->
                    <fo:region-body margin-top="0cm" margin-bottom="1cm"/>
                    <!--页脚//-->
                    <fo:region-after extent="0.5cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <xsl:apply-templates select="PageList"/>
        </fo:root>
    </xsl:template>
    

    <xsl:template match="PageList">
        <fo:page-sequence>
            <!--定义页面样式引用，可以为首页、单数页、偶数页定义不同的页面风格-->
            <fo:sequence-specification>
                <fo:sequence-specifier-alternating
                    page-master-first="main"
                    page-master-odd="main"
                    page-master-even="main"/>
            </fo:sequence-specification>
            <!--页眉显示内容-->
            <fo:static-content flow-name="xsl-region-before">
                <fo:block line-height="1pt" font-size="1pt" text-align="end"></fo:block>
            </fo:static-content>
            <!--页脚显示内容-->
            <fo:static-content flow-name="xsl-region-after">
            <fo:block line-height="10pt" font-size="10pt" text-align="center">第<fo:page-number/>页  共 <fo:page-number-citation ref-id="endofdoc"/> 页</fo:block>
            </fo:static-content>
            <!--页面主体内容-->
            <fo:flow flow-name="xsl-region-body">
                <!--报表头-->
                <xsl:apply-templates select="ReportHeader"/>
                <!--报表体(若有多个部分内容，参照下面一行重复)-->
                <xsl:apply-templates select="ReportBody"/>
                <!--报表尾-->
                <xsl:apply-templates select="ReportFooter"/>
            </fo:flow>
        </fo:page-sequence>
    </xsl:template>
    
    
          <!--报表主体（一般只有一个表格）//-->
     <xsl:template match="ReportBody">
        <!--空行,第一联和后两联不同,头联空1行,后两联空2行,有动态xml控制-->
        
        <xsl:apply-templates select="TableBegin"/>
        <xsl:apply-templates select="TableLetter"/>
        <xsl:apply-templates select="TableEnd"/>
       
      
        
      </xsl:template> 
      
      
      
      
     <xsl:template match="TableLetter"> 
     
      <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
      我根据<xsl:value-of select="productName"/>网（网址www.touziwuyou.com）公示的规则，申请加入<xsl:value-of select="productName"/>网的会员，并通过<xsl:value-of select="productName"/>网向提出借款请求的借款人投资。为依法建立法律关系，适当善意履行合同，维护参与各方当事人的利益，特郑重承诺如下：</fo:block>
     
     <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
     一、我依据<xsl:value-of select="productName"/>网公示的规则，根据<xsl:value-of select="productName"/>网的指引注册为会员的行为，表明我完全接受<xsl:value-of select="productName"/>网公示在网站上的与投资人有关的一切规范。</fo:block>
     
     <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
   二、我已充分知晓<xsl:value-of select="productName"/>网对我仅仅提供撮合服务，而不提供任何投资安全保证。</fo:block>
     
     <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
   三、我已充分了解通过<xsl:value-of select="productName"/>网向借款人投资的风险，因此，我的每一项投资均自愿、理性、谨慎而为，并且具有承担可能产生的投资风险的能力。</fo:block>
     
     <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
    四、我依据借款人发布在<xsl:value-of select="productName"/>网上的“借款标”向借款人投资的行为，表明我完全接受<xsl:value-of select="productName"/>网公示在网站上的《借款与服务合同》，无条件确认该合同对我产生法律约束力，无条件接受该合同赋予甲方即投资人的权利和义务。</fo:block>
     
     <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
     五、我在<xsl:value-of select="productName"/>网，依据网络系统指引对本承诺书表示同意或者不同意的选项中选择“同意”的操作行为，即表明我完全接受与确认本承诺书所载明的事项。</fo:block>
     
      <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
     特此承诺</fo:block>
     
     
     
    </xsl:template>   
      

    
    
    <xsl:template match="TableBegin">
    	<fo:table space-before.optimum="10pt" border-collapse="separate" font-size="10pt"  text-align="right"  border-width="0pt" border-style="solid" border-color="yellow">
	  
	   <fo:table-column column-width="1cm"/>
	   <fo:table-column column-width="8cm"/>
	    <fo:table-column column-width="4cm"/>
	   <fo:table-column column-width="3cm"/>
	  
       <!-- 定义表头 // -->
         <fo:table-header>
	     <fo:table-row height="10pt">
	         <fo:table-cell number-columns-spanned="4"  height="10pt">
	             <fo:block text-align="left"  wrap-option="wrap"> 编号： <xsl:value-of select="LETTERNO"/></fo:block>
	         </fo:table-cell>
	          
	     </fo:table-row>
	     </fo:table-header>
	   
         
	     
	     
	     <fo:table-body  text-align="left"  >
	     
	     
	     
	         <fo:table-row height="15pt">
		          <fo:table-cell  number-columns-spanned="4" >
		             <fo:block wrap-option="wrap" ></fo:block>
		          </fo:table-cell>
	          </fo:table-row>
	     
	     
	          <fo:table-row height="30pt">
		          <fo:table-cell number-columns-spanned="4"  height="40pt" border-width="0pt" border-style="solid" border-color="blue">
		             <fo:block text-align="center"  wrap-option="wrap"  font-weight="bold" font-size="20pt">承诺书</fo:block>
		          </fo:table-cell>
	          </fo:table-row>
	          
	          <fo:table-row height="20pt">
		          <fo:table-cell  >
		             <fo:block wrap-option="wrap" font-size="12pt">致：</fo:block>
		          </fo:table-cell>
		          <fo:table-cell  >
		             <fo:block  wrap-option="wrap" font-size="12pt" >重庆易八投资服务有限公司</fo:block>
		          </fo:table-cell>
		          <fo:table-cell >
		             <fo:block  number-columns-spanned="2"  ></fo:block>
		         </fo:table-cell>
	          </fo:table-row>
	          
	          <fo:table-row height="20pt">
		          <fo:table-cell  >
		             <fo:block wrap-option="wrap" ></fo:block>
		          </fo:table-cell>
		          <fo:table-cell  >
		             <fo:block  wrap-option="wrap" font-size="12pt"  >为借款人提供后备贷款服务的小额贷款公司</fo:block>
		          </fo:table-cell>
		          <fo:table-cell >
		             <fo:block  number-columns-spanned="2"  ></fo:block>
		         </fo:table-cell>
	          </fo:table-row>
	          
	           <fo:table-row height="20pt">
		          <fo:table-cell  >
		             <fo:block wrap-option="wrap" ></fo:block>
		          </fo:table-cell>
		          <fo:table-cell  >
		             <fo:block  wrap-option="wrap" font-size="12pt" >在<xsl:value-of select="productName"/>网发布“借款标”的借款人</fo:block>
		          </fo:table-cell>
		          <fo:table-cell >
		             <fo:block  number-columns-spanned="2"  ></fo:block>
		         </fo:table-cell>
	          </fo:table-row>
	          
	          
	          
	         
         </fo:table-body>
      </fo:table>
   </xsl:template>
      
      
    <xsl:template match="TableEnd">
    
     <fo:table space-before.optimum="10pt" border-collapse="separate" font-size="12pt"  text-align="center"  border-width="0pt" border-style="solid" border-color="green">
	   <fo:table-column column-width="6cm"/>
	   <fo:table-column column-width="8cm"/>
	   <fo:table-column column-width="2cm"/>
       <!-- 定义表头 //-->
         <fo:table-header>
	     <fo:table-row height="20pt">
	         <fo:table-cell   height="20pt"   number-columns-spanned="3">
	             <fo:block text-align="center"  wrap-option="wrap"></fo:block>
	         </fo:table-cell>
	     </fo:table-row>
	     
	      <fo:table-row height="10pt">
	         
	         <fo:table-cell  number-columns-spanned="3" >
	             <fo:block text-align="right"  wrap-option="wrap" font-size="12pt" >承诺人：<xsl:value-of select="productName"/>网会员</fo:block>
	         </fo:table-cell>
	          
	     </fo:table-row>
	     
	       <fo:table-row height="20pt">
	         <fo:table-cell   height="30pt"   number-columns-spanned="3">
	             <fo:block text-align="center"  wrap-option="wrap"></fo:block>
	         </fo:table-cell>
	         
	         
	     </fo:table-row>
	     <!-- 
	      <fo:table-row >
	         <fo:table-cell  >
	             <fo:block text-align="center"  wrap-option="wrap"></fo:block>
	         </fo:table-cell>
	         <fo:table-cell  >
	             <fo:block text-align="center"  wrap-option="wrap"></fo:block>
	         </fo:table-cell>
	         <fo:table-cell  >
	             <fo:block text-align="right"  wrap-option="wrap" ><xsl:value-of select="PYYYY"/>年<xsl:value-of select="PMM"/> 月<xsl:value-of select="PDD"/> 日</fo:block>
	         </fo:table-cell>
	     </fo:table-row>
	     
	      -->
	     
	     </fo:table-header>
	     <fo:table-body>
         </fo:table-body>
      </fo:table>
    </xsl:template>  
       
       
    
      <xsl:template match="ReportFooter">
      <fo:block id="endofdoc"></fo:block>
      </xsl:template>   
  
  
  
  </xsl:stylesheet>  
    