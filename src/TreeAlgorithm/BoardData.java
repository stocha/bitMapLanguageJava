/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TreeAlgorithm;

import java.util.List;

/**
 *
 * @author denis
 */
public interface BoardData {
    List<BoardData> getSubData();
    double scoreOnce();
}
