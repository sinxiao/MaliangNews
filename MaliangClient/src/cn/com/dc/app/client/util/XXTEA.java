package cn.com.dc.app.client.util;

/**
 *
 * @author Camus
 */
public class XXTEA 
{
    private byte[] key;
    
    public XXTEA()
    {
    }
    
    public void setKey(String _key)
    {
        key = _key.getBytes();
    }
    
    public byte[] encrypt(byte[] data) 
    {
        if (data.length == 0) 
        {
            return data;
        }
        return toByteArray(encrypt(toIntArray(data, true), toIntArray(key, false)), false);
    }

    public byte[] decrypt(byte[] data) 
    {
        if (data.length == 0)
        {
            return data;
        }
        return toByteArray(decrypt(toIntArray(data, false), toIntArray(key, false)), true);
    }

    private int[] encrypt(int[] v, int[] k) 
    {
        int n = v.length - 1;
        if (n < 1) 
        {
            return v;
        }
        if (k.length < 4)
        {
            int[] key = new int[4];
            System.arraycopy(k, 0, key, 0, k.length);
            k = key;
        }
        int z = v[n], y = v[0], delta = 0x9E3779B9, sum = 0, e;
        int p, q = 6 + 52 / (n + 1);
        while (q-- > 0)
        {
            sum = sum + delta;
            e = sum >>> 2 & 3;
            for (p = 0; p < n; p++)
            {
                y = v[p + 1];
                z = v[p] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
            }
            y = v[0];
            z = v[n] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
        }
        return v;
    }

    private int[] decrypt(int[] v, int[] k)
    {
        int n = v.length - 1;
        if (n < 1) 
        {
            return v;
        }
        if (k.length < 4) 
        {
            int[] key = new int[4];
            System.arraycopy(k, 0, key, 0, k.length);
            k = key;
        }
        int z = v[n], y = v[0], delta = 0x9E3779B9, sum, e;
        int p, q = 6 + 52 / (n + 1);
        sum = q * delta;
        while (sum != 0) 
        {
            e = sum >>> 2 & 3;
            for (p = n; p > 0; p--) 
            {
                z = v[p - 1];
                y = v[p] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
            }
            z = v[n];
            y = v[0] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
            sum = sum - delta;
        }
        return v;
    }

    private int[] toIntArray(byte[] data, boolean includeLength) 
    {
        int n = (((data.length & 3) == 0) ? (data.length >>> 2) : ((data.length >>> 2) + 1));
        int[] result;
        if (includeLength) 
        {
            result = new int[n + 1];
            result[n] = data.length;
        } 
        else 
        {
            result = new int[n];
        }
        n = data.length;
        for (int i = 0; i < n; i++) 
        {
            result[i >>> 2] |= (0x000000FF & data[i]) << ((i & 3) << 3);
        }
        return result;
    }

    private byte[] toByteArray(int[] data, boolean includeLength) 
    {
        int n = data.length << 2;
        if (includeLength) 
        {
            int m = data[data.length - 1];
            if (m > n) 
            {
                return null;
            } 
            else 
            {
                n = m;
            }
        }
        byte[] result = new byte[n];
        for (int i = 0; i < n; i++) 
        {
            result[i] = (byte) ((data[i >>> 2] >>> ((i & 3) << 3)) & 0xFF);
        }
        return result;
    }
    
}
