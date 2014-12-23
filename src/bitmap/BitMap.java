/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bitmap;

/**
 *
 * @author jahan
 */
public class BitMap {

    public final int x;
    public final int y;

    public final int xnbmap;

    public final long[][] maps;

    public final long borderMask;
    public final int decsup;

    public BitMap(BitMap model){
        this(model.x,model.y);
    }

    public static BitMap callBuffer1=null;

    public boolean isZero(){
        long bits=0;

        for(int i=0;i<y;i++){
            for(int k=0;k<xnbmap;k++){
                bits|=maps[i][k];
            }
        }

        return bits==0;
    }


    public void floodFillUp(BitMap path,BitMap buff1,BitMap buff2,BitMap buff3,BitMap buff4){
        buff4.copy(this);
        while(!buff4.isZero()){
            buff4.copy(this);
            this.scrambleRotate(buff1, buff2, buff3, 1);
            this.and(path);
            buff4.xor(this);
        }    
    }

    public boolean nearestUpTo(BitMap dest,BitMap d0,BitMap d1,BitMap d2,BitMap d3,
            BitMap path,
            BitMap buffsave,BitMap buffAccumul,BitMap buffShift){
        // Trouver les points les plus proches entre this et dest, et met
        // les dest sur le front de prochitude dans la map correspondant a la direction d'arrivee
        // this contient le front d'onde

        //System.err.println(""+this);
        //System.err.println(""+dest);

        d0.xor(d0);
        d1.xor(d1);
        d2.xor(d2);
        d3.xor(d3);

        // This will be saved.(1)
        // This will grow out into each direction once.
        // Each direction growth will be fused into this again

        // the fused (this) will be compared with the save(1)

        buffsave.set(0,0,1);
        buffAccumul.copy(this);
        boolean found=false;

        int secure=(dest.x+dest.y)<<1;
        while(!buffsave.isZero()&&!found && secure>0){
            secure--;
            buffsave.copy(this);

            d0.copy(this);
            d0.rotateUp(1,buffShift);
            buffAccumul.or(d0);
            d0.and(dest);

            d2.copy(this);
            d2.rotateDown(1,buffShift);
            buffAccumul.or(d2);
            d2.and(dest);

            d3.copy(this);
            d3.rotateLeft(1,buffShift);
            buffAccumul.or(d3);
            d3.and(dest);

            d1.copy(this);
            d1.rotateRight(1,buffShift);
            buffAccumul.or(d1);
            d1.and(dest);

            this.copy(buffAccumul);
            buffAccumul.and(dest); // is there any touched
            this.and(path); // restriction au chemin
            buffsave.xor(this);
            found=!buffAccumul.isZero();
            //System.err.println(""+this);
        }

        return found;
        
    }

    public void intersectUpTo(BitMap dest,BitMap bmff01,BitMap bmff02,BitMap bmff03){
        this.scrambleRotate(bmff01, bmff02, bmff03, 1);
        this.and(dest);
    }

    public void scrambleRotate(BitMap buff1,BitMap buff2,BitMap buff3,int l){
        buff1.copy(this);
        buff1.rotateDown(l, buff2);
        buff3.copy(buff1);

        buff1.copy(this);
        buff1.rotateUp(l, buff2);
        buff3.or(buff1);
        
        buff1.copy(this);
        buff1.rotateLeft( l,buff2);
        buff3.or(buff1);

        buff1.copy(this);
        buff1.rotateRight( l,buff2);
        buff3.or(buff1);

        this.or(buff3);
    }

    public BitMap(int x,int y){
        this.x=x;
        this.y=y;

        int nbmaps=x>>>6;
        int sup=x&63;
        if(sup >0){ nbmaps++; }

        borderMask=((-1L>>>(64-sup)));
        this.decsup=sup;
        this.xnbmap=nbmaps;

        maps=new long[y][];

        for(int i=0;i<y;i++){
            maps[i]=new long[xnbmap];
        }
    }

    public void shiftLeft(int nb){

        shiftLeftMeta(nb>>>6);

        nb=nb&63;
        if(nb==0) return;
        for(int i=0;i<y;i++){
            for(int k=0;k<xnbmap-1;k++){
                maps[i][k]>>>=nb;
                maps[i][k]|=(maps[i][k+1]<<(64-nb));
            }
            maps[i][xnbmap-1]>>>=nb;
            maps[i][xnbmap-1]&=borderMask;

            //maps[i][0]=borderMask;
        }
    }
    public void shiftRight(int nb){

        shiftRightMeta(nb>>>6);

        nb=nb&63;
        if(nb==0) return;        
        for(int i=0;i<y;i++){
            for(int k=xnbmap-1;k>0;k--){
                maps[i][k]<<=nb;
                maps[i][k]|=(maps[i][k-1]>>>(64-nb));
            }
            maps[i][0]<<=nb;
            maps[i][xnbmap-1]&=borderMask;
        }
    }

    public void rotateLeftMicro(int nb){
        nb=nb&63;
        if(nb==0) return;
        for(int i=0;i<y;i++){
            long buff=maps[i][0];
            for(int k=0;k<xnbmap-1;k++){
                maps[i][k]>>>=nb;
                maps[i][k]|=(maps[i][k+1]<<(64-nb));
            }
            maps[i][xnbmap-1]>>>=nb;
            maps[i][xnbmap-1]|=(buff<<(64-nb+decsup));
            maps[i][xnbmap-1]&=borderMask;

        }
    }
    public void rotateLeftMacro(int nb,BitMap buff){
        for(int i=xnbmap-nb;i<xnbmap;i++){
            for(int k=0;k<y;k++){
                buff.maps[k][i]=maps[k][i-xnbmap+nb];
            }
        }
        for(int i=0;i<xnbmap-nb;i++){
            for(int k=0;k<y;k++){
                maps[k][i]=maps[k][i+nb];
            }
        }
        for(int i=xnbmap-nb;i<xnbmap;i++){
            for(int k=0;k<y;k++){
                maps[k][i]=buff.maps[k][i];//buff.maps[i][k];
            }
        }
    }

    public void rotateLeft(int nb,BitMap buff){
        buff.copy(this);
        this.shiftLeft(nb);
        buff.shiftRight(x-nb);
        this.or(buff);
    }
    public void rotateRight(int nb,BitMap buff){
        buff.copy(this);
        this.shiftRight(nb);
        buff.shiftLeft(x-nb);
        this.or(buff);
    }

//    @Deprecated public void rotateLeft(int nb){
//        rotateLeft(nb, callBuffer1);
//    }
//    @Deprecated public void rotateRight(int nb){
//        rotateRight(nb, callBuffer1);
//    }

    public void rotateRightMicro(int nb){
        nb=nb&63;
        if(nb==0) return;
        for(int i=0;i<y;i++){
            long buff=maps[i][xnbmap-1];
            for(int k=xnbmap-1;k>0;k--){
                maps[i][k]<<=nb;
                maps[i][k]|=(maps[i][k-1]>>>(64-nb));
            }
            maps[i][0]<<=nb;
            maps[i][0]|=(buff>>>(64-nb+decsup));
            //maps[i][0]|=buff;
            maps[i][xnbmap-1]&=borderMask;
        }
    }

    public void rotateUp(int nb,BitMap buff){
        for(int i=y-nb;i<y;i++){
            for(int k=0;k<xnbmap;k++){
                buff.maps[i][k]=maps[i-y+nb][k];
            }
        }
        for(int i=0;i<y-nb;i++){
            for(int k=0;k<xnbmap;k++){
                maps[i][k]=maps[i+nb][k];
            }
        }
        for(int i=y-nb;i<y;i++){
            for(int k=0;k<xnbmap;k++){
                maps[i][k]=buff.maps[i][k];//buff.maps[i][k];
            }
        }
    }

    public void rotateDown(int nb,BitMap buff){
        for(int i=nb-1;i>=0;i--){
            for(int k=0;k<xnbmap;k++){
                buff.maps[i][k]=maps[i+y-nb][k];
            }
        }

        for(int i=y-1;i>=nb;i--){
            for(int k=0;k<xnbmap;k++){
                maps[i][k]=maps[i-nb][k];
            }
        }
        for(int i=nb-1;i>=0;i--){
            for(int k=0;k<xnbmap;k++){
                maps[i][k]=buff.maps[i][k];
            }
        }
    }

    public void shiftLeftMeta(int nb){
        if(nb==0 || nb > xnbmap) return;
        for(int i=0;i<xnbmap-nb;i++){
            for(int k=0;k<y;k++){
                maps[k][i]=maps[k][i+nb];
            }
        }
        for(int i=xnbmap-nb;i<xnbmap;i++){
            for(int k=0;k<y;k++){
                maps[k][i]=0;
            }
        }
    }

    public void shiftUp(int nb){
        for(int i=0;i<y-nb;i++){
            for(int k=0;k<xnbmap;k++){
                maps[i][k]=maps[i+nb][k];
            }
        }
        for(int i=y-nb;i<y;i++){
            for(int k=0;k<xnbmap;k++){
                maps[i][k]=0;
            }
        }
    }

    public void shiftRightMeta(int nb){

        if(nb==0 || nb >xnbmap) return;

        for(int i=xnbmap-1;i>=nb;i--){
            for(int k=0;k<y;k++){
                maps[k][i]=maps[k][i-nb];
            }
        }
        for(int i=nb-1;i>=0;i--){
            for(int k=0;k<y;k++){
                maps[k][i]=0;
            }
        }
    }

    public void shiftDown(int nb){
        for(int i=y-1;i>=nb;i--){
            for(int k=0;k<xnbmap;k++){
                maps[i][k]=maps[i-nb][k];
            }
        }
        for(int i=nb-1;i>=0;i--){
            for(int k=0;k<xnbmap;k++){
                maps[i][k]=0;
            }
        }
    }

    public void secureSet(int x,int y, long val){
        if(x<0 || y<0 || x>= this.x || y>= this.y) return;

        set(x,y,val);
    }

    public void set(int x,int y, long val){


        long row[]=maps[y];

        int num=x>>>6;
        int bit=x&63;

        long bitval=1L<<bit;
        row[num]&=~(bitval);
        row[num]|=(bitval&(val<<bit));
    }

    public long get(int x,int y){
        long row[]=maps[y];

        int num=x>>>6;
        int bit=x&63;

        return ((row[num]>>>bit)&1L);
    }

    public void and(BitMap m){
        for(int i=0;i<maps.length;i++){
            long tm[]=this.maps[i];
            long om[]=m.maps[i];

            for(int j=0;j<tm.length;j++){
                tm[j]&=om[j];
            }
        }
    }

    public void or(BitMap m){
        for(int i=0;i<maps.length;i++){
            long tm[]=this.maps[i];
            long om[]=m.maps[i];

            for(int j=0;j<tm.length;j++){
                tm[j]|=om[j];
            }
        }
    }

    public void xor(BitMap m){
        for(int i=0;i<maps.length;i++){
            long tm[]=this.maps[i];
            long om[]=m.maps[i];

            for(int j=0;j<tm.length;j++){
                tm[j]^=om[j];
            }
        }
    }

    public void bitnot(){

        for(int j=0;j<y;j++){
            for(int n=0;n<xnbmap;n++){
                maps[j][n]=~maps[j][n];
            }
            maps[j][xnbmap-1]&=borderMask;
        }

    }

    public void copy(BitMap m){
        for(int i=0;i<maps.length;i++){
            long tm[]=this.maps[i];
            long om[]=m.maps[i];

            for(int j=0;j<tm.length;j++){
                tm[j]=om[j];
            }
        }
    }

    public String toString(){
        StringBuilder sb=new StringBuilder();
        for(int j=0;j<y;j++){
            for(int i=0;i<x;i++){
            

                char c='.';

                if(get(i,j)!=0) c='X';
                sb.append(c);
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public String debug_fullBitMapView(){
        StringBuilder sb=new StringBuilder();
        for(int j=0;j<y;j++){
            for(int i=0;i<(this.xnbmap<<6);i++){


                char c='.';

                if(get(i,j)!=0) c='X';
                sb.append(c);

                if(i==x-1) sb.append('|');
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean putFirstBitInto(BitMap extract){
        BitMap src=this;
        extract.xor(extract);
        for(int j=0;j<src.y;j++){
            for(int ib=0;ib<src.xnbmap;ib++){
                long v=src.maps[j][ib];
                if(v!=0){
                    long k=v;
                    v &= v - 1L;
                    k^=v;
                    extract.maps[j][ib]=k;
                    return true;
                }
            }
        }
        return false;
    }

    public int bitCount(){
        BitMap src=this;
        int res=0;
        for(int j=0;j<src.y;j++){
            for(int ib=0;ib<src.xnbmap;ib++){
                long v=src.maps[j][ib];
                while(v!=0){
                    long k=v;
                    v &= v - 1L;
                    k^=v;
                    res+=1;
                }
            }

        }
        return res;
    }




    public static void diskToAllPointRotate(BitMap src,BitMap buff,BitMap dest,BitMap buff2, int sqrad){

        dest.xor(dest);
        for(int i=0;i*i<=sqrad;i++){
            for(int j=0;i*i+j*j<=sqrad;j++){

                buff.copy(src);
                buff.rotateRight(i,buff2);
                buff.rotateDown(j,buff2);
                dest.or(buff);

                buff.copy(src);
                buff.rotateLeft(i,buff2);
                buff.rotateDown(j,buff2);
                dest.or(buff);

                buff.copy(src);
                buff.rotateRight(i,buff2);
                buff.rotateUp(j,buff2);
                dest.or(buff);

                buff.copy(src);
                buff.rotateLeft(i,buff2);
                buff.rotateUp(j,buff2);
                dest.or(buff);
            }
        }
    }


    public static void test_Rotate(){

        int sz=65+128;
        BitMap src=new BitMap(sz,5);
        BitMap buff=new BitMap(sz,5);
        BitMap buffCall=new BitMap(sz,5);
        BitMap.callBuffer1=buffCall;

        System.err.println("To the left <---");
        src.xor(src);
        for(int j=0;j<src.y;j++){
            for(int i=0;i<src.x;i++){
                if(i==src.x-1 || i==0){
                    src.set(i,j,1);
                }
            }
        }
        for(int i=0;i<src.x;i++){
            buff.copy(src);
            buff.rotateLeft(i,buffCall);
            System.err.println(""+buff.debug_fullBitMapView());
        }

        System.err.println("To the right --->");
//        for(int j=0;j<src.y;j++){
//            for(int i=0;i<src.x;i++){
//                if(i==0){
//                    src.set(i,j,1);
//                }
//            }
//        }

        for(int i=0;i<src.x;i++){
            buff.copy(src);
            buff.rotateRight(i,buffCall);
            System.err.println(""+buff.debug_fullBitMapView());
        }

        
        
    }

    public static void test_shift(){

        int sz=65+128;
        BitMap src=new BitMap(sz,5);
        BitMap buff=new BitMap(sz,5);
        BitMap buffCall=new BitMap(sz,5);
        BitMap.callBuffer1=buffCall;

        System.err.println("To the left <---");
        src.xor(src);
        for(int j=0;j<src.y;j++){
            for(int i=0;i<src.x;i++){
                if(i==src.x-1){
                    src.set(i,j,1);
                }
            }
        }
        for(int i=0;i<src.x;i++){
            buff.copy(src);
            buff.shiftLeft(i);
            System.err.println(""+buff.debug_fullBitMapView());
        }


        System.err.println("To the right --->");
        src.xor(src);
        for(int j=0;j<src.y;j++){
            for(int i=0;i<src.x;i++){
                if(i==0){
                    src.set(i,j,1);
                }
            }
        }

        for(int i=0;i<src.x;i++){
            buff.copy(src);
            buff.shiftRight(i);
            System.err.println(""+buff.debug_fullBitMapView());
        }



    }

    public static int numFirstBit(long bits){
        for(int i=0;i<64;i++){
            if(((bits>>i)&1) == 1) return i;
        }
        return 64;
    }

    public void getFirstBitXy(int[] res){
        BitMap src=this;
        for(int j=0;j<src.y;j++){
            int posxoffset=0;
            for(int ib=0;ib<src.xnbmap;ib++){
                long v=src.maps[j][ib];
                while(v!=0){
                    long k=v;
                    v &= v - 1L;
                    k^=v;
                    res[0]=posxoffset+numFirstBit(k);
                    res[1]=j;
                    //System.err.println("found x = "+(posxoffset+numFirstBit(k)));
                    //System.err.println("found y = "+j);
                    return;
                }
                posxoffset+=64;
            }

        }
    }

    public static void test_ShowCircles(){
        BitMap bm[]=new BitMap[10];


        int waitcount=-1;
        for(int sz=20;sz<128;sz++){
            for(int i=0;i<bm.length;i++){
                bm[i]=new BitMap(sz,sz);
            }


            bm[0].xor(bm[0]);
            bm[0].set(0, 0, 1);
            diskToAllPointRotate(bm[0], bm[1], bm[2], bm[3], 81);

            int currcount=bm[2].bitCount();
            if(waitcount==-1){
                waitcount=currcount;
            }
            System.err.println(""+bm[2].debug_fullBitMapView()+"\n sz="+sz +"  count="+currcount);

            if(waitcount!=currcount){
                throw new RuntimeException("Problem avec les bitmaps");
            }
        }


    }

}
