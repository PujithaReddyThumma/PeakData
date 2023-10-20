/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author admin
 */
import weka.core.Instances;
//import weka.core.OptionHandler;
//import weka.core.Utils;
import java.util.*;
import weka.filters.Filter;
import java.util.BitSet;

import java.io.FileReader;
import java.io.BufferedReader;
//import java.util.Vector;
//import weka.filters.supervised.attribute.NominalToBinary;
import weka.filters.unsupervised.attribute.Remove;

/* class bucket1
     {
        public int count;
        public double sum;
        
         public void bucket(double x,int y)
         {
             this.sum=x;
             this.count=y;
         }
     }
*/
public class finaltrackpop1 {
    
   public static Instances data,data2;
   
      pubic static int pv;
      public static BitSet global,global1;
     public static bucket bc,bc1;
     public static int no_results=0;
    public static Map<String,bucket> map=new Hashtable<String,bucket>(); 
    long no_buck=500000,scan=0,no_condates=0,no_remove=0;
        double thres=650000;
                //2500;
        int second_scan=0,lineno=-1,slineno=0;        
        String dataset="E:\\ex\\datanormal.txt";
     
   public finaltrackpop1()throws Exception {   
            }
    public static void main(String[] args) throws Exception {
         global =new BitSet();
         
         
    finaltrackpop1 obj=new finaltrackpop1();
    
    obj.first_scan();
    }
        public void first_scan()throws Exception
        {
        
        
         BufferedReader in = new BufferedReader(new FileReader(dataset));
          double value=0.0;
          String str,str1,key;
         long start = System.currentTimeMillis();
         ////////////////////////////////////////////////////////////////////////////first scan
         
         while ((str = in.readLine()) != null) { 
                           
                                      lineno++;
                                      StringTokenizer st=new StringTokenizer(str,",");
                           key="";
                           
                           //key,value divided from record
                           while(st.hasMoreTokens()){
                           key=key+st.nextToken();
                           //System.out.println("key\n"+key+"count"+st.countTokens());
                               if(st.countTokens()==1)
                                {
                                    value=Integer.parseInt(st.nextToken());
                                    //return value;
                                break;
                                }
       
                          }  //end  key,value divided from record
            
         
                        if(bucket_fill(key,value))
                       {    
                           
                           
                        
                            } else{ 
        System.out.println("number of records read in first scan  "+lineno);
         //System.out.println("bucket size  "+map.size());
         
                          bucket_remove();
       //bucket_fill(key,value);
     
                     if(map.size()<no_buck)
                       {
                           second_scan=0;
                           bucket_fill(key,value);
                           //global1.set(lineno); 
                           //System.out.println("******************");
                       }else{
                         second_scan=1;
                         }
                       }
       //scecond scan
      
      if(second_scan==1)
      {
         pv=pv+ findpeakData();
          no_condates=no_condates+map.size();
          bucket_reset();
          second_scan();
       bucket_fill(key,value);
      // global1.set(lineno); 
       //System.out.println("#######");
      }
      
      }
          System.out.println("number of records read in first scan  "+lineno);
          if(map.size()>0)
          {  
              bucket_remove();
              bucket_reset();
              no_condates=no_condates+map.size();
              second_scan();
              
              }
          long time = System.currentTimeMillis() - start;
          System.out.println("\nexecution time i milliseconds :"+time);
          System.out.println("\n number of condiates :"+no_condates);
          System.out.println("\n number of seco11nd scans :"+scan);
        System.out.println("\n number of remove :"+no_remove);
        System.out.println("\n number of results :"+no_results);
System.out.println("\n number of peakdata :"+pv);
         //System.out.println("\n cardinadlity :"+global1.cardinality());
         //global.flip(0,99999999);
         //System.out.println("\n cardinadlity :"+global);
        }
      public int findpeakData(){
           int peak=0;
          Iterator iterator = map.keySet().iterator();
             
       while (iterator.hasNext()) {
          
        key1 = iterator.next().toString();
          bucket bc2 = map.get(key1);
          
           if(bc2.count ==1)
           {
               peak++;
return peak;
                          
           }
        
         
              
             


       boolean bucket_fill(String key,double value){
                               if(map.get(key)==null)
                                    {      
                                        if(map.size()<no_buck){
                                            //global1.set(lineno); 
                                         if(!global.get(lineno))                                         
                                         {
                                             if(value>=thres){
                                         bc=new bucket();
                                         bc.count=1;
                                          bc.sum=value;
                                           //num++;
                    
                                            map.put(key, bc);
                                             }
                                            //System.out.println("line no"+lineno+" new hash table  with key is"+key+"\n");
                                            return true;
                                    
                                         }
                                    }else{
                                        return false;
                                         }
            
                                        }else{
                                  // global1.set(lineno); 
             
                                            bc=map.get(key);
                                            bc.count=bc.count+1;
                                //System.out.println("line no"+lineno+"hash table updates with key is"+key+"\n");
                                             bc.sum=bc.sum+value;
                                                map.put(key,bc);
                                                return true;
                                            }
                               return true;
                       }
       void  bucket_remove()
             {
                 no_remove++;
                 String key1;
            Iterator iterator = map.keySet().iterator();
             
       while (iterator.hasNext()) {
          
        key1 = iterator.next().toString();
          bucket bc2 = map.get(key1);
          
           if(bc2.sum/bc2.count < thres)
           {
               iterator.remove();
                            
           }
               
           } 
       System.out.println("removed============================\n");
       System.out.println("after removed bucket size is  \n"+map.size()+"\n");
             }
       void  bucket_reset()
             {
                 String key1;
                    Iterator iterator1 = map.keySet().iterator();
             
       while (iterator1.hasNext()) {
          
        key1 = iterator1.next().toString();
          bucket bc2 = map.get(key1);          
           bc2.sum=0;
           bc2.count=0;
           map.put(key1,bc2);
               
           } 
       System.out.println("Bucket reset============================\n");
      
             }
        void  second_scan()throws Exception
          {
              double value=0.0;
              String str1,key1;
              scan++;
          System.out.println("second scan start================="+scan+"\n");
       //System.out.println("number of bukets  thresh"+map.size());
       BufferedReader sin = new BufferedReader(new FileReader(dataset));
      
      slineno=-1;
      while ((str1 = sin.readLine()) != null) {
          slineno++;
        
           //System.out.println(slineno+":"+str1);
      StringTokenizer sst=new StringTokenizer(str1,",");
                           key1="";
                           while(sst.hasMoreTokens()){
                           key1=key1+sst.nextToken();
                               if(sst.countTokens()==1)
                                {
                                    value=Integer.parseInt(sst.nextToken());
                                break;
                                }
       
                          }
      
       if(map.get(key1)!=null)
        {
           bc=map.get(key1);
          bc.count=bc.count+1;
          //System.out.println("hash table updates with key is"+key+"\n");
          bc.sum=bc.sum+ value;
          map.put(key1,bc);
          global.set(slineno);
          
             
        }
       
           }//end second scan
      System.out.println("second scan end================="+global.cardinality()+"\n");
      
      ///results
        Iterator iterator1 = map.keySet().iterator();
       while (iterator1.hasNext()) {
       key1 = iterator1.next().toString();
          bucket bc3 = map.get(key1);
         
          if((bc3.sum/bc3.count)>thres)
           {
               no_results++;
           
           }
          //System.out.println(key1 + " " + bc3.count+"    "+bc3.sum+"average==="+bc3.sum/bc3.count);
                         
           } // chech tresh evalu on hole data
       System.out.println("result count upto now"+no_results);
       //System.out.println(global);
               map.clear();
               second_scan=0;
          }

        }