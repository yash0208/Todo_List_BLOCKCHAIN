package com.rajaryan.todo_list.manager;

import android.content.Context;
import android.widget.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rajaryan.todo_list.Adapter.BlockAdepter;
import com.rajaryan.todo_list.model.BlockModel;

import java.util.ArrayList;
import java.util.List;

public class BlockChainManager {
    private int difficulty;
    private List<BlockModel> blockModels;
    public final BlockAdepter adepter;

    public BlockChainManager(int difficulty, @NonNull Context context) {
        this.difficulty = difficulty;
        blockModels=new ArrayList<>();
        BlockModel block=new BlockModel(0,System.currentTimeMillis(),null,"Genesis Block");
        block.MineBlock(difficulty);
        blockModels.add(block);
        adepter =new BlockAdepter(blockModels,context);
    }
    private BlockModel latestBlock(String data){
        BlockModel latestBlock=lastestBlock();
        return new BlockModel(latestBlock.getIndex()+1,System.currentTimeMillis(),latestBlock.getHash(),data);
    }

    private BlockModel lastestBlock() {
        return blockModels.get(blockModels.size()-1);
    }
    public void addBlock(BlockModel blockModel1){
        if (blockModel1!=null){
            blockModel1.MineBlock(difficulty);
            blockModels.add(blockModel1);
        }
    }
    private boolean isFirstBlockValid(){
        BlockModel firstBlock =blockModels.get(0);
        if(firstBlock.getIndex()!=0){
            return false;
        }
        if (firstBlock.getPreviousHash()!=null){
            return false;
        }
        return firstBlock.getHash()!=null && BlockModel.calculate_hash(firstBlock).equals(firstBlock.getHash());
    }
    private boolean isValidBlock(@Nullable BlockModel newBlock,@Nullable BlockModel previous){
        if(newBlock!=null && previous!=null){
            if (previous.getIndex()+1!=newBlock.getIndex()){
                return false;
            }
            if(newBlock.getPreviousHash()!=null || !newBlock.getPreviousHash().equals(newBlock.getData())){
                return  false;
            }
            return newBlock.getHash()!=null && BlockModel.calculate_hash(newBlock).equals(newBlock.getHash());
        }
        return false;
    }
    public boolean isBlockChainValid(){
        if(!isFirstBlockValid()){
            return false;
        }
        for(int i=1;i<blockModels.size();i++){
            BlockModel current=blockModels.get(i);
            BlockModel previous=blockModels.get(i-1);
            if(!isValidBlock(current,previous)){
                return false;
            }
        }
        return true;
    }

    public BlockModel newBlock(String data) {

        BlockModel latestBlock=lastestBlock();
        return new BlockModel(latestBlock.getIndex()+1,System.currentTimeMillis(),latestBlock.getHash(),data);
    }
}
